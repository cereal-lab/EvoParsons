package evoparsons.ecj;

import ec.EvolutionState;
import ec.SelectionMethod;
import ec.util.Parameter;
/*
 * Implements selection mechanism in Bucci, 2003 paper 
 */
public class EverySelection extends SelectionMethod	
{	
	private static final long serialVersionUID = 1L;
	private int index;
	public static final String P_EVERY_SELECTION = "everySelection";

	public void prepareToProduce(final EvolutionState s,
			final int subpopulation,
			final int thread)	
	{
		index = 0;	
	}

	public int produce(final int subpopulation,
			final EvolutionState state,
			final int thread)	
	{
		int currentIndex = index; 
		index = (index + 1) % state.population.subpops[0].individuals.length;
		return currentIndex;
	}

	public Parameter defaultBase() {
		return ec.simple.SimpleDefaults.base().push(P_EVERY_SELECTION);
	}	

}
