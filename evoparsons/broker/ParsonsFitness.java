package evoparsons.broker;

import java.util.function.BiPredicate;
import java.util.stream.IntStream;

public class ParsonsFitness {

	public final ParsonsGenotype first;
	public final ParsonsGenotype second;
	public final boolean firstIsDominant;
	public final boolean secondIsDominant;
	
	private ParsonsFitness(ParsonsGenotype first, ParsonsGenotype second, boolean firstIsDominant, boolean secondIsDominant) {
		this.first = first;
		this.second = second;
		this.firstIsDominant = firstIsDominant;
		this.secondIsDominant = secondIsDominant;
	}

	public static ParsonsFitness Create(ParsonsGenotype first, double[] firstValues, 
		ParsonsGenotype second, double[] secondValues)
	{
		/* If I dominate then my game value is 1 (pair is 0) 
		 * If my pair dominate then pair game value is 1 (me is 0)
		 * if both non-dominate then both game value is -1**/

		BiPredicate<double[], double[]> isParetoDominant = 
			(me, other) -> IntStream.range(0, me.length).allMatch(i -> me[i] > other[i]);
		
		if (isParetoDominant.test(firstValues, secondValues)) 
		{
			return new ParsonsFitness(first, second, true, false);
		}
		else if (isParetoDominant.test(secondValues, firstValues)) // Me is dominated
			{
				return new ParsonsFitness(first, second, false, true);
			}
		else  // both are non-dominated
			{
				return new ParsonsFitness(first, second, false, false);
			}						
	}	
	
	@Override
	public String toString() {
		return String.format("{'first':%s,'second':%s,'dominant':'%s'}", first.toString(), second.toString(),
			this.firstIsDominant ? "first" : (this.secondIsDominant ? "second": ""));
	}

}
