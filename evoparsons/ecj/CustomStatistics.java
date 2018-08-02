package evoparsons.ecj;

import java.util.Arrays;

import ec.EvolutionState;
import ec.Individual;
import ec.simple.SimpleStatistics;
import ec.vector.IntegerVectorIndividual;
/*
 * Prints all the problets in every generations. It also adds  their hash value. 
 * Note: normally we need not any hash values for previous experiemnts. So, we 
 * keep that CustomStatistics as it was and just add the hash values
 * in this version. Reusability of previous customstatistics is still possible
 * by supplying parameter (say whichExp = ictai, flair, evoparssons etc.) from configuration files.
 */
public class CustomStatistics extends SimpleStatistics 
{

	private static final long serialVersionUID = 1L;
	boolean warned = false;
	private static final int HASHTABLE_SIZE = 10000;

	public void postEvaluationStatistics(final EvolutionState state)	
	{
		super.postEvaluationStatistics(state);         
		Individual[] best_i = new Individual[state.population.subpops.length];

		for(int x=0;x<state.population.subpops.length;x++)	
		{
			best_i[x] = state.population.subpops[x].individuals[0];

			/**Print all the problets  and their hash values**/
			for (int m = 0; m <state.population.subpops[x].individuals.length; m++)
			{
				state.population.subpops[x].individuals[m].printIndividualForHumans(state, statisticslog);
				IntegerVectorIndividual ind = (IntegerVectorIndividual)state.population.subpops[x].individuals[m];
				int hash = (Arrays.hashCode(ind.genome) & 0x7FFFFFFF) % HASHTABLE_SIZE;
				state.output.println(Integer.toString(hash), statisticslog);
			}

			for(int y=1;y<state.population.subpops[x].individuals.length;y++)	
			{
				if (state.population.subpops[x].individuals[y] == null)	
				{
					if (!warned)	
					{
						state.output.warnOnce("Null individuals found in subpopulation");
						warned = true;  
					}
				}
				else if (best_i[x] == null || state.population.subpops[x].individuals[y].fitness.betterThan(best_i[x].fitness))
					best_i[x] = state.population.subpops[x].individuals[y];
				
				if (best_i[x] == null)	
				{
					if (!warned)	
					{
						state.output.warnOnce("Null individuals found in subpopulation");
						warned = true; 
					}
				}
			}

			if (best_of_run[x]==null || best_i[x].fitness.betterThan(best_of_run[x].fitness))
				best_of_run[x] = (Individual)(best_i[x].clone());            
		}
	}
}
