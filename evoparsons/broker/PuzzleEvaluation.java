package evoparsons.broker;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import evoparsons.rmishared.ParsonsEvaluation;
import evoparsons.rmishared.ParsonsPuzzle;

class PuzzleEvaluation implements Serializable {
	private static final long serialVersionUID = 1L;
	public final ParsonsGenotype genotype;
	public final Map<Integer, ParsonsEvaluation> evaluations;	
	public final int generation;		
	public PuzzleEvaluation(ParsonsGenotype genotype, int generation) {
		this.genotype = genotype;
		this.evaluations = new HashMap<>();
		this.generation = generation;
	}	
	public PuzzleEvaluation(ParsonsGenotype genotype, Map<Integer, ParsonsEvaluation> evaluations, int generation) {
		this.genotype = genotype;
		this.evaluations = evaluations;
		this.generation = generation;
	}	
	
	public static final PuzzleEvaluation dummy = 
		new PuzzleEvaluation(
			new ParsonsGenotype(
				new ParsonsPuzzle(-1, "Congratulations!", "You completed all the required puzzles", 
					Collections.emptyList(), 
					Collections.emptyList()))
			, 0);
}