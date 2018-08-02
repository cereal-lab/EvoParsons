package evoparsons.ecj;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import ec.EvolutionState;
import ec.simple.SimpleEvolutionState;
import ec.util.Parameter;
import ec.vector.IntegerVectorIndividual;
import evoparsons.broker.EvolutionAlgorithm;
import evoparsons.broker.Log;
import evoparsons.broker.ParsonsFitness;
import evoparsons.broker.ParsonsGenotype;
import evoparsons.ecj.MutatorEvoParsons.Pair;

public class ParsonsEvolutionState extends SimpleEvolutionState {
	private static final long serialVersionUID = 1L;
	private transient EvolutionAlgorithm alg;
	private transient BiConsumer<ParsonsGenotype, ParsonsGenotype> genotypeEvolutionRecorder =
		(before, after) -> {};
	private Map<ParsonsGenotype.Pair, Pair<IntegerVectorIndividual, IntegerVectorIndividual>> parentChildPairs;
	//TODO: split EvolutionAlgorithm interface
	public void setEvolutionAlgorithm(EvolutionAlgorithm alg) {
		this.alg = alg;
	}
	public EvolutionAlgorithm getEvolutionAlgorithm() {
		return alg;
	}
	public void setGenotypeEvolutionRecorder(BiConsumer<ParsonsGenotype, ParsonsGenotype> genotypeEvolutionRecorder) {
		this.genotypeEvolutionRecorder = genotypeEvolutionRecorder;
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
		Log log = alg.getBroker().getLog();
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
					genotypeEvolutionRecorder.accept(firstSecondPair.parent, firstSecondPair.parent);				
				} else {
					log.log("[ParsonsEvolutionState.setFitness] Child %s dominates over %s", firstSecondPair.child.toString(), firstSecondPair.parent.toString());
					genotypeEvolutionRecorder.accept(firstSecondPair.parent, firstSecondPair.child);
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
					genotypeEvolutionRecorder.accept(secondFirstPair.parent, secondFirstPair.parent);									
				} else {
					log.log("[ParsonsEvolutionState.setFitness] Child %s dominates over %s", secondFirstPair.child.toString(), secondFirstPair.parent.toString());
					genotypeEvolutionRecorder.accept(secondFirstPair.parent, secondFirstPair.child);
				}
			}
			parentChildPairs.notify();
		}
	}	

}