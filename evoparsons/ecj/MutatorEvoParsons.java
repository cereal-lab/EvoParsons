package evoparsons.ecj;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ec.BreedingPipeline;
import ec.EvolutionState;
import ec.Individual;
import ec.util.Parameter;
import ec.vector.IntegerVectorIndividual;
import ec.vector.IntegerVectorSpecies;
import ec.vector.VectorDefaults;
import evoparsons.broker.Broker;
import evoparsons.broker.Log;
import evoparsons.broker.ParsonsGenotype;
import evoparsons.broker.ParsonsGenotypeIndex;
/*
 * Sending parents and childs puzzle to the broker and wait for game decision
 */
public class MutatorEvoParsons extends BreedingPipeline 
{		
	public  static final String EVOPARSONS_MUTATOR = "evoparsons-mutation";
	
	private int problemMax;
	private int transformMax;
	//private static EvolutionState state;
	private transient Map<ParsonsGenotype.Pair, Pair<IntegerVectorIndividual, IntegerVectorIndividual>> genotypePairs;
	
	private static final long serialVersionUID = 1L;  
	
			
	public Parameter defaultBase() {
		return VectorDefaults.base().push(EVOPARSONS_MUTATOR);	
	}   
	public int numSources(){ 
		return 1;	
	}
	
	public void prepareToProduce(final EvolutionState state,
	        final int subpopulation,
	        final int thread)
    {
		Log log = ((ParsonsEvolutionState)state).getEvolutionAlgorithm().getBroker().getLog();
		log.log("[MutatorEvoParsons.prepareToProduce] GEN %d started", state.generation);
		genotypePairs = new HashMap<>();
        if (sources[0] == null)  {
            state.output.fatal("Stub not filled in Breeding Pipeline.");
            }
        sources[0].prepareToProduce(state,subpopulation,thread);
    }

	
	public void setup(final EvolutionState state, final Parameter base)	
	{
		super.setup(state,base);	
		problemMax = state.parameters.getInt(new Parameter("pop.subpop.0.species.max-gene.0"), null);
		transformMax = state.parameters.getInt(new Parameter("pop.subpop.0.species.max-gene"), null);
	}

	public static class Pair<K, V> {
		public K first;
		public V second;
		public Pair(K f, V s) {
			first = f; second = s;
		}
	}

	public int produce(final int min, 
			final int max, 
			final int start,
			final int subpopulation,
			final Individual[] inds,
			final EvolutionState state,
			final int thread)	
	{	
		Broker broker = ((ParsonsEvolutionState)state).getEvolutionAlgorithm().getBroker();
		ParsonsGenotypeIndex genotypeStore = broker.getGenotypeStore();
		Log log = broker.getLog();

		int n = sources[0].produce(min,max,start,subpopulation,inds,state,thread);

		if (!state.random[thread].nextBoolean(likelihood))
			return reproduce(n, start, subpopulation, inds, state, thread, false);  

		if (!(sources[0] instanceof BreedingPipeline))
			for(int q=start;q<n+start;q++)
				inds[q] = (Individual)(inds[q].clone());

		if (!(inds[start] instanceof IntegerVectorIndividual)) 
			state.output.fatal("evoparsons mutation class didn't get an IntegerVectorIndividual." +
					"The offending individual is in subpopulation " + subpopulation + " and it's:" + inds[start]);


		IntegerVectorSpecies species = (IntegerVectorSpecies)(inds[start].species);     
		
		Map<ParsonsGenotype.Pair, Pair<IntegerVectorIndividual, IntegerVectorIndividual>> pairs = 
			IntStream.range(start, n + start)			
				.mapToObj(q -> {
					/**As our no selection pulls the individual sequentially by index, cross-over does not happen and a single same child tossed
					 we get child and parent same individual here. Except, there pointers are different. Child is the pointer of new individuals
					whereas parent is the pointer of old individuals. Hence, if you mutate child it only mutates the new individuals.

					clone is not necessary here becuase it is a breeding pipeline, not a selection method. However, you can do that and does not
					affect anyone except the operation cost of clone
					***/
					IntegerVectorIndividual child = (IntegerVectorIndividual)inds[q];
					child.evaluated = false;
					IntegerVectorIndividual parent = (IntegerVectorIndividual)state.population.subpops[subpopulation].individuals[q];     

					IntStream.range(0, child.genome.length)
						//.skip(1) //do not evaluate programs
						.filter(x -> state.random[thread].nextBoolean(species.mutationProbability(x)))
						.forEach(x ->
							{
								int valueMax = (x == 0) ? problemMax : transformMax;
								if (state.random[thread].nextBoolean())
									child.genome[x] = (child.genome[x] + 1) % valueMax;
								else 
									child.genome[x] = (child.genome[x] == 0) ? (valueMax - 1) : (child.genome[x] - 1);	
							});
									
					ParsonsGenotype.Pair parentAndChild = 
						genotypeStore.createPair(parent.genome, child.genome);						
					log.log("Pair: (%d, %d)%n\t%s%n\t%s", 
						parentAndChild.parent.getIndex(), parentAndChild.child.getIndex(),
						Arrays.stream(parent.genome).mapToObj(i -> String.format("%3d", i)).collect(Collectors.joining(" ")),
						Arrays.stream(child.genome).mapToObj(i -> String.format("%3d", i)).collect(Collectors.joining(" "))
						);
					return new Pair<>(parentAndChild, new Pair<>(parent, child));				        						
				}).collect(Collectors.toMap(pair -> pair.first, pair -> pair.second));
		genotypePairs.putAll(pairs);
		return n;
	}

	public void finishProducing(final EvolutionState state,
			final int subpopulation,
			final int thread)
	{		
		List<ParsonsGenotype> genotypes = 
			genotypePairs.entrySet().stream().flatMap(pair -> Arrays.asList(pair.getKey().parent, pair.getKey().child).stream()).collect(Collectors.toList());
		ParsonsEvolutionState parsonsState = (ParsonsEvolutionState)state;
		Log log = parsonsState.getEvolutionAlgorithm().getBroker().getLog();
		parsonsState.setParentChildPairs(genotypePairs);
		parsonsState.getEvolutionAlgorithm().getBroker().setGenotypes(genotypes, state.generation);
		parsonsState.getEvolutionAlgorithm().getBroker().getGenotypeStore().save();		
		log.log("[MutatorEvoParsons.finishProducing] Waiting for evaluated parent and child pair");		
		((ParsonsEvolutionState)state).waitEvaluation();
		log.log("[MutatorEvoParsons.finishProducing] GEN %d ended", state.generation);				
		sources[0].finishProducing(state,subpopulation,thread);
	}    	        	
}


