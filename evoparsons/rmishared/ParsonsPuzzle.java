package evoparsons.rmishared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Models the data exchanged from the {@link ParsonsBroker} to the ITS client
 * e.g. {@link PSI} or {@ ParsonsProblet} so far. Represents a Parsons Puzzle.
 **/
public class ParsonsPuzzle implements Serializable
{
	public static class AppliedDistracter implements Serializable {
		private static final long serialVersionUID = 1L;
		public final int distracterId;
		public final String distractedLine;
		public AppliedDistracter(int distracterId, String distractedLine) {
			this.distracterId = distracterId;
			this.distractedLine = distractedLine;
		}
	}
	private static final long serialVersionUID = 1L;
	// protected List<Line> phenotype;
	public final int id;
	public final String title;
	public final String description;	
	public final List<String[]> program;			
	public final List<AppliedDistracter> distracters;

	public ParsonsPuzzle(int id, String title, String desc, List<String[]> program, 
		List<AppliedDistracter> distracters) {
		this.id = id;
		this.title = title;
		this.description = desc;
		this.program = program;
		this.distracters = distracters;
	}

	public List<Fragment> buildFragments()
	{		
		List<Fragment> fragments = 
			new ArrayList<>(IntStream.range(0, program.size())
				.mapToObj(i -> new Fragment(String.join(System.lineSeparator(), program.get(i)), i, null))
				.collect(Collectors.toList()));
		
		List<Fragment> distractors = 
			distracters.stream()
				.map(distracter -> new Fragment(distracter.distractedLine, -1, distracter))
				.collect(Collectors.toList());

		fragments.addAll(distractors);
		
		Collections.shuffle(fragments);
		
		return fragments;
	}
}
