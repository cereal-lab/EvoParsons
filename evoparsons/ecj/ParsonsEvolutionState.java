package evoparsons.ecj;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ec.EvolutionState;
import ec.simple.SimpleEvolutionState;
import ec.util.Parameter;
import ec.vector.IntegerVectorIndividual;
import evoparsons.broker.BrokerEAInterface;
import evoparsons.broker.Config;
import evoparsons.broker.Log;
import evoparsons.broker.ParsonsFitness;
import evoparsons.broker.ParsonsGenotype;
import evoparsons.broker.ParsonsGenotypeIndex;
import evoparsons.broker.Utils;
import evoparsons.ecj.MutatorEvoParsons.Pair;

public class ParsonsEvolutionState extends SimpleEvolutionState {
	private static final String DEFAULT_GENOTYPE_EVOL_FILE = "genotypeEvol.bro";
	private static final long serialVersionUID = 1L;
	private transient BrokerEAInterface broker;
	private transient Log log = Log.suppress;
	private transient Config config;
	private transient ParsonsGenotypeIndex genotypeFactory;
	private transient Map<ParsonsGenotype, List<ParsonsGenotype>> genotypeEvolution;
	private Map<ParsonsGenotype.Pair, Pair<IntegerVectorIndividual, IntegerVectorIndividual>> parentChildPairs;
	private String genotypeEvolFile;
	//TODO: split EvolutionAlgorithm interface
	public ParsonsEvolutionState withConfig(Log log, Config config) {
		this.log = log;
		this.config = config;
		genotypeEvolFile = Paths.get(config.getOutputFolder(log), config.get("evoparsons.genotypeEvolFile", DEFAULT_GENOTYPE_EVOL_FILE)).toString();
		this.genotypeEvolution =
			Utils.<Map<ParsonsGenotype, List<ParsonsGenotype>>>loadFromFile(log, genotypeEvolFile, 
				HashMap<ParsonsGenotype, List<ParsonsGenotype>>::new);
		if (genotypeEvolution.size() == 0)
			log.log("[ParsonsEvolutionState.withConfig] genotype evolution map is empty");
		else 
			log.log("[ParsonsEvolutionState.withConfig] %d evolution chains were srestored from %s", genotypeEvolution.size(), genotypeEvolFile);					
		return this;
	}
	public ParsonsEvolutionState withGenotypeFactory(ParsonsGenotypeIndex genotypeFactory) {
		this.genotypeFactory = genotypeFactory;
		return this;
	}
	public ParsonsGenotypeIndex getGenotypeFactory() {
		return genotypeFactory;
	}
	public Log getLog() { return log; }
	public ParsonsEvolutionState withBroker(BrokerEAInterface broker) {
		this.broker = broker;
		if (this.broker != null)
			broker.setFitnessConsumer(this::setFitness);
		return this;
	}

	public void setGenotypes(List<ParsonsGenotype> genotypes, int generation) {
		if (this.broker != null)
			this.broker.setGenotypes(genotypes, generation);
		else {
			log.err("[ParsonsEvolutionState] setGenotypes is noop. Broker was not set");
		}
	}

	public ParsonsEvolutionState start(int state) {
		super.run(state);
		return this;
	}
    @Override
    public void setup(EvolutionState state, Parameter arg1) {
		super.setup(state, arg1);
		parentChildPairs = new HashMap<>();
    }

	public void waitEvaluation()
	{
		synchronized (parentChildPairs)
		{
			while (!parentChildPairs.isEmpty())
				try {
					parentChildPairs.wait();
				} catch (InterruptedException e) {				
				}
		}
	}   

	public void setParentChildPairs(Map<ParsonsGenotype.Pair, Pair<IntegerVectorIndividual, IntegerVectorIndividual>> individuals) {
		synchronized (parentChildPairs)
		{
			parentChildPairs.putAll(individuals);
		}
	}

	public void setFitness(ParsonsFitness fitness)
	{
		//int [] treasures = new int[2]; //[0] = index, 0 to popSize - 1. [1] = whoami, 10 = parent, 20 = child 
		synchronized (parentChildPairs)
		{
			ParsonsGenotype.Pair firstSecondPair = new ParsonsGenotype.Pair(fitness.first, fitness.second);
			Pair<IntegerVectorIndividual, IntegerVectorIndividual> parentAndChildIndividuals = parentChildPairs.remove(firstSecondPair);
			if (parentAndChildIndividuals != null)
			{
				if (fitness.firstIsDominant || !fitness.secondIsDominant)
				{
					if (!fitness.firstIsDominant)
						log.log("[ParsonsEvolutionState.setFitness] There is no domination between %s and %s", firstSecondPair.parent.toString(), firstSecondPair.child.toString());
					else 
						log.log("[ParsonsEvolutionState.setFitness] Parent %s dominates over %s", firstSecondPair.parent.toString(), firstSecondPair.child.toString());
					parentAndChildIndividuals.second.setGenome(parentAndChildIndividuals.first.genome);
					recordGenotypeEvolution(firstSecondPair.parent, firstSecondPair.parent);				
				} else {
					log.log("[ParsonsEvolutionState.setFitness] Child %s dominates over %s", firstSecondPair.child.toString(), firstSecondPair.parent.toString());
					recordGenotypeEvolution(firstSecondPair.parent, firstSecondPair.child);
				}				
			}
			ParsonsGenotype.Pair secondFirstPair = new ParsonsGenotype.Pair(fitness.second, fitness.first);
			parentAndChildIndividuals = parentChildPairs.remove(secondFirstPair);
			if (parentAndChildIndividuals != null)
			{
				if (fitness.secondIsDominant || !fitness.firstIsDominant)
				{
					if (!fitness.secondIsDominant)					
						log.log("[ParsonsEvolutionState.setFitness] There is no domination between %s and %s", secondFirstPair.parent.toString(), secondFirstPair.child.toString());
					else 
						log.log("[ParsonsEvolutionState.setFitness] Parent %s dominates over %s", secondFirstPair.parent.toString(), secondFirstPair.child.toString());
					parentAndChildIndividuals.second.setGenome(parentAndChildIndividuals.first.genome);			
					recordGenotypeEvolution(secondFirstPair.parent, secondFirstPair.parent);									
				} else {
					log.log("[ParsonsEvolutionState.setFitness] Child %s dominates over %s", secondFirstPair.child.toString(), secondFirstPair.parent.toString());
					recordGenotypeEvolution(secondFirstPair.parent, secondFirstPair.child);
				}
			}
			parentChildPairs.notify();
		}
	}	

	void recordGenotypeEvolution(ParsonsGenotype before, ParsonsGenotype after) {                
		List<ParsonsGenotype> genotypeHistory = genotypeEvolution.remove(before);
		if (genotypeHistory == null) {
			genotypeHistory = new LinkedList<>();
			genotypeHistory.add(before);
			genotypeHistory.add(after);
			genotypeEvolution.put(after, genotypeHistory);
		} else {
			genotypeHistory.add(after);
			genotypeEvolution.put(after, genotypeHistory);
		}
		Utils.saveToFile(log, genotypeEvolution, genotypeEvolFile);
	}

}