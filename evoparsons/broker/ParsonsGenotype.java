package evoparsons.broker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import evoparsons.rmishared.ParsonsPuzzle;

/**
 * This class is used to pass the genotype, the program to be evaluated, from
 * the EA to the broker. The genotype is passed as an array of integers.
 * 
 * @author paul
 *
 */
public class ParsonsGenotype implements Serializable
{
	private static final long serialVersionUID = 1L;	
	private ParsonsPuzzle puzzle;
	public static class Pair {
		public ParsonsGenotype parent;
		public ParsonsGenotype child;
		public Pair(ParsonsGenotype p, ParsonsGenotype c) { parent = p; child = c; }
		@Override
		public int hashCode() { return Objects.hash(parent, child); }
		@Override 
		public boolean equals(Object o) {
			return (o instanceof Pair) && parent.equals(((Pair)o).parent) && child.equals(((Pair)o).child);
		}
	}
	public ParsonsGenotype(ParsonsGenotypeIndex store, int[] genome, int[] pairedGenome, boolean isParent) { 
		this.genome = genome; 
		this.index = store.remapGenotype(genome);
		this.pairedIndex = store.remapGenotype(pairedGenome);
	}

	public ParsonsGenotype(ParsonsPuzzle puzzle) {
		this.genome = new int[0];
		this.index = -1;
		this.pairedIndex = -1;
		this.puzzle = puzzle;
	}

	public ParsonsGenotype(ParsonsGenotypeIndex store, int... genome) {
		this.genome = genome;
		this.index = store.remapGenotype(genome);
		this.pairedIndex = -1;
	}

	public ParsonsGenotype(ParsonsGenotypeIndex store, IntStream genes) {
		this.genome = genes.toArray();
		this.index = store.remapGenotype(genome);
		this.pairedIndex = -1;
	}	

	public ParsonsPuzzle getPuzzle(ParsonsLibrary lib) {
		if (puzzle == null) {
			puzzle = this.buildPuzzle(lib);
		}
		return puzzle;
	}	

	private ParsonsPuzzle buildPuzzle(ParsonsLibrary lib) {

		Program program = lib.getProgram(genome[0]);
				
		class Tuple<K, V> {
			public K first;
			public V second;
			public Tuple(K f, V s){
				first = f; second = s;
			}
		}		

		Map<Integer, Tuple<Pattern, String>> distractors = 
			Arrays.stream(genome)
				.skip(1) //skip the program index
				.distinct()
				.mapToObj(Integer::valueOf)				
				.collect(Collectors.toMap(i -> i, 
					i -> new Tuple<>(lib.getTransform(i).getPattern(), lib.getTransform(i).getTransform())));

		
		class Tmp {
			public List<ParsonsPuzzle.AppliedDistracter> distracters;
			public Map<Integer, Tuple<Pattern, String>> leftPatterns;
			public Tmp(Map<Integer, Tuple<Pattern, String>> leftPatterns) {
				distracters = new ArrayList<>();
				this.leftPatterns = new HashMap<>(leftPatterns);
			}
		} 

		List<String[]> fragmentLines = 
			program.body
				.stream()
				.map(line -> 
					Arrays.stream(line.split("\\\\\\\\n"))
						.map(l -> l.trim())
						.collect(Collectors.toList()).toArray(new String[0]))
				.collect(Collectors.toList());

		List<String> programFragments = 
			fragmentLines.stream()
				.map(l -> String.join(System.lineSeparator(), l))
				.collect(Collectors.toList());

		Tmp distractionResult = 
			programFragments
				.stream()
				.collect(
					() -> new Tmp(distractors),
					(acc, programLine) -> {
						new HashMap<>(acc.leftPatterns).entrySet().stream()
							.map(distractorEntry ->
								new Tuple<>(distractorEntry, distractorEntry.getValue().first.matcher(programLine)))
							.filter(distractorWithMatcher -> distractorWithMatcher.second.matches())
							.forEach(foundElement -> 
								{
									Integer distractorId = foundElement.first.getKey();
									acc.leftPatterns.remove(distractorId);
									Matcher match = foundElement.second;
									String replacement = foundElement.first.getValue().second;
									acc.distracters.add(new ParsonsPuzzle.AppliedDistracter(distractorId, match.replaceFirst(replacement)));
								});
					},
					(acc1, acc2) -> {}
				);
			
		return 
			new ParsonsPuzzle(index, 
					program.title, program.description, fragmentLines,
					distractionResult.distracters);		
	} 
	
	@Override
	public int hashCode() { return index; }
	
	@Override
	public boolean equals(Object o) { return (o instanceof ParsonsGenotype) && (((ParsonsGenotype)o).index == index);} 

	public int getIndex() { return index; }
	public int getPairedIndex() { return pairedIndex; }
	protected int[] genome;
	protected int index;	
	protected int pairedIndex;

	@Override
	public String toString() {
		return String.format("[%s]", Arrays.stream(genome).mapToObj(String::valueOf).collect(Collectors.joining(",")));
	}
}
