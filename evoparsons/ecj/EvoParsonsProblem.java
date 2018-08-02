package evoparsons.ecj;

import ec.EvolutionState;
import ec.Individual;
import ec.Population;
import ec.Problem;
import ec.simple.SimpleFitness;
import ec.simple.SimpleProblemForm;
import ec.vector.IntegerVectorIndividual;
/*
 * update objective fitness of the parsons puzzles
 */
public class EvoParsonsProblem extends Problem implements SimpleProblemForm	
{    

	private static final long serialVersionUID = 1L;

	public void preprocessPopulation(final EvolutionState state, Population pop, boolean[] updateFitness, boolean countVictoriesOnly)	{ return;  }

	@Override
	public void evaluate(EvolutionState state, Individual ind, int subpop, int thread) {
		if( ! ( ind instanceof IntegerVectorIndividual ) )
			state.output.fatal( "The individuals in the Problem should be IntegerVectorIndividuals." );
		IntegerVectorIndividual tempMe = (IntegerVectorIndividual)ind;
		double myObjFitness = 0;
		
		for (int x = 0; x < tempMe.genome.length; x++)
		{
		    myObjFitness +=  tempMe.genome[x];
		}

		SimpleFitness fit = ((SimpleFitness)(ind.fitness));
		fit.setFitness(state, myObjFitness, false);
		
	}
	
	public void evaluate(final EvolutionState state,
			final Individual[] ind,  // the individuals to evaluate together
			final boolean[] updateFitness,  
			final boolean countVictoriesOnly,
			int[] subpops,
			final int threadnum)	
	{
		if( ind.length != 1 || updateFitness.length != 1)
			state.output.fatal( "The InternalSumProblem evaluates only one individual at a time." );

		if( ! ( ind[0] instanceof IntegerVectorIndividual ) )
			state.output.fatal( "The individuals in the Problem should be IntegerVectorIndividuals." );

		IntegerVectorIndividual tempMe = (IntegerVectorIndividual)ind[0];
		double myObjFitness = 0;
		
		for (int x = 0; x < tempMe.genome.length; x++)
		  {
		    myObjFitness +=  tempMe.genome[x];
		  }

		if( updateFitness[0] )	
		{
			SimpleFitness fit = ((SimpleFitness)(ind[0].fitness));
			fit.setFitness(state, myObjFitness, false);
		}


	}

}





