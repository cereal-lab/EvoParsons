package evoparsons.broker;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import evoparsons.rmishared.ParsonsEvaluation;

public interface SelectionPolicy
{	
	public Optional<PuzzleEvaluation> select(int studentId, 
		Map<Integer, PuzzleEvaluation> available, 
		Map<Integer, PuzzleEvaluation> evaluated, 
		Map<Integer, PuzzleEvaluation> giveUps, 
		//Optional<PuzzleEvaluation> lastSeen,
		Log logger);
		
	// public default void setSeen(int studentId, PuzzleEvaluation eval) {
	// 	InternalState.lastSeen.put(studentId, eval.genotype.getIndex());		
	// }

	public default Optional<PuzzleEvaluation> select(int studentId, Map<Integer, PuzzleEvaluation> currentGeneration, Log logger) {
		//Integer lastSeenPuzzleIndex = InternalState.lastSeen.get(studentId);		
		// PuzzleEvaluation lastSeenPuzzle = 
		// 	(lastSeenPuzzleIndex == null) ? null : currentGeneration.get(lastSeenPuzzleIndex);
		class Tmp {
			public final Map<Integer, PuzzleEvaluation> available = new HashMap<>();
			public final Map<Integer, PuzzleEvaluation> evaluated = new HashMap<>();
			public final Map<Integer, PuzzleEvaluation> giveUps = new HashMap<>();
			// public final Optional<PuzzleEvaluation> lastSeen = 
			// 	(lastSeenPuzzle == null) ? Optional.empty() : Optional.of(lastSeenPuzzle);
		}
		Tmp tmp =
			currentGeneration
				.entrySet()
				.stream()
				.collect(() -> new Tmp(), 
					(acc, entry) -> {
						//if (!acc.lastSeen.isPresent() || (entry.getValue() != acc.lastSeen.get())) {
						if (entry.getValue().evaluations.containsKey(studentId)) {
							if (entry.getValue().evaluations.get(studentId).gaveUp)
								acc.giveUps.put(entry.getKey(), entry.getValue());
							else 
								acc.evaluated.put(entry.getKey(), entry.getValue());
						} else 
							acc.available.put(entry.getKey(), entry.getValue());
//						}
					},
					(acc1, acc2) -> {}
				);
		Optional<PuzzleEvaluation> selectedPuzzle = 
			this.select(studentId, tmp.available, tmp.evaluated, tmp.giveUps, logger);
		if (!selectedPuzzle.isPresent()) 
			selectedPuzzle = Utils.randFromMap(currentGeneration);
		return selectedPuzzle;
	}

	public abstract class LimitedSelectionPolicy implements SelectionPolicy {
		protected int limit = 0;
		public LimitedSelectionPolicy(int limit) { this.limit = limit; }
		public SelectionPolicy then(SelectionPolicy nextPolicy) {
			SelectionPolicy that = this;
			return 
				(studentId, avaliable, evaluated, giveUps, logger) -> 
				{
					Optional<PuzzleEvaluation> puzzleOpt = 
						that.select(studentId, avaliable, evaluated, giveUps, logger);
					if (puzzleOpt.isPresent()) return puzzleOpt;
					else 
						return 		
							(evaluated.size() >= limit) 
								? nextPolicy.select(studentId, avaliable, evaluated, giveUps, logger)
								: Optional.empty();
				};
		}
	}

	public static final SelectionPolicy cycling = 
		(studentId, available, evaluated, giveUps, logger) ->
		{
			Optional<PuzzleEvaluation> puzzleOpt = 
				available.entrySet().stream().findFirst()
					.map(entry -> entry.getValue());
			if (puzzleOpt.isPresent())
				return puzzleOpt;
			else 
				return 
					Utils.randFromMap(giveUps);				
		};

	public default LimitedSelectionPolicy limit(int n) {
		SelectionPolicy that = this;
		return 
			new LimitedSelectionPolicy(n) {
				@Override 
				public Optional<PuzzleEvaluation> select(int studentId, Map<Integer, PuzzleEvaluation> avaliable, Map<Integer, PuzzleEvaluation> evaluated, Map<Integer, PuzzleEvaluation> giveUps, Log logger)
				{					
					if (evaluated.size() < limit) {
						return that.select(studentId, avaliable, evaluated, giveUps, logger);
					} else 
						return Optional.empty();
				}
			};
	}

	// store here state for policies if only it is required
	// better way is to use LocalState - see limit() example
	public static class InternalState {
		private static final double EPSILON = 0.15;
		private static final Random rand = new Random();
		//private static Map<Integer, Map<Integer, Long>> lastSeen = new HashMap<>();		
	}	

	public static final SelectionPolicy pairing =
		(studentId, available, evaluated, giveUps, logger) -> {
			//rule 1: we have pair in current generation where one is evaluated but other is not
			Optional<PuzzleEvaluation> puzzleOpt = 
				evaluated
					.entrySet()
					.stream()
					.filter(entry -> available.containsKey(entry.getValue().genotype.pairedIndex))
					.findFirst()
					.map(entry -> available.get(entry.getValue().genotype.pairedIndex));
			if (puzzleOpt.isPresent()) return puzzleOpt;
			Map<Integer, PuzzleEvaluation> all = new HashMap<>(available);
			all.putAll(giveUps);
			//all.putAll(evaluated);
			List<Entry<Long, List<Entry<Integer, PuzzleEvaluation>>>> allListGroupedBySeen = 
				all.entrySet()
					.stream()
					.collect(
						Collectors.groupingBy(keyValue -> {
							ParsonsEvaluation eval = keyValue.getValue().evaluations.get(studentId);
							if (eval == null) return Long.MIN_VALUE;
							else return eval.timestamp;
						}))
					.entrySet()
					.stream()
					.sorted(Comparator.comparing(e -> e.getKey()))
					.collect(Collectors.toList());
			// class Pair<K, V> {
			// 	public final K first;
			// 	public final V second;
			// 	public Pair(K k, V v) {
			// 		this.first = k;
			// 		this.second = v;
			// 	}
			// }
			List<Entry<Integer, PuzzleEvaluation>> allListGroupedBySeenAndCount = 
				allListGroupedBySeen.stream()
					.flatMap(e -> {
						return //new Pair<>(e.getKey(), 
							e.getValue().stream()
								.collect(Collectors.groupingBy(entry -> entry.getValue().evaluations.size()))
								.entrySet()
								.stream()
								.sorted(Comparator.comparing(entry -> entry.getKey()))
								.flatMap(entry -> entry.getValue().stream());
								//.collect(Collectors.toList());
					})
					.collect(Collectors.toList());
			int lastSeenProgramIndex =
				(allListGroupedBySeenAndCount.size() > 0) ?
					allListGroupedBySeenAndCount.get(allListGroupedBySeenAndCount.size() - 1).getValue().genotype.genome[0]
					: -1;
			allListGroupedBySeenAndCount = 
				allListGroupedBySeenAndCount
					.stream()
					.filter(entry -> entry.getValue().genotype.genome[0] != lastSeenProgramIndex)
					.collect(Collectors.toList());
			// Optional<PuzzleEvaluation> olderInHistory = 
			// 	allListGroupedBySeenAndCount.stream().findFirst()
			// 		.flatMap(keyValue -> 
			// 			keyValue.second.stream().findFirst().map(entry -> entry.getValue()));
			// if (olderInHistory.isPresent() 
			//     && olderInHistory.get().evaluations.containsKey(studentId)
			// 	&& !olderInHistory.get().evaluations.get(studentId).gaveUp
			// 	&& available.containsKey(olderInHistory.get().genotype.pairedIndex)) {
			// 		return Optional.of(available.get(olderInHistory.get().genotype.pairedIndex));
			// }
			//rule 2: we need to select random pair from non evaluated yet. we do this by min number of evaluations (as in completness)
			// this rule takes into account giveUp flag
			//Check first available map
			//Map<Integer, PuzzleEvaluation> availableAndGiveUps = new HashMap<>(available);
			//availableAndGiveUps.putAll(giveUps);
			// List<Entry<Integer, PuzzleEvaluation>> evals = 
			// 	allListSortedBySeen
			// 		.stream()
			// 		.collect(Collectors.groupingBy(entry -> entry.getValue().evaluations.size()))
			// 		.entrySet()
			// 		.stream()
			// 		.sorted(Comparator.comparing(e -> e.getKey()))
			// 		.flatMap(entry -> entry.getValue().stream())
			// 		.collect(Collectors.toList());

			puzzleOpt = 
				allListGroupedBySeenAndCount
				.stream()
				.filter(entry -> 
					available.containsKey(entry.getKey())
					&& available.containsKey(entry.getValue().genotype.pairedIndex))
				.findFirst()
				.map(entry -> entry.getValue());							
			if (puzzleOpt.isPresent()) return puzzleOpt;
			puzzleOpt = 
				allListGroupedBySeenAndCount
					.stream()
					.filter(entry -> 
						available.containsKey(entry.getKey())
							&& giveUps.containsKey(entry.getValue().genotype.pairedIndex))
					.findFirst()
					.map(entry -> entry.getValue());
			if (puzzleOpt.isPresent()) return puzzleOpt;
			// puzzleOpt = 
			// 	allListGroupedBySeenAndCount
			// 	.stream()
			// 	.filter(entry -> 
			// 		giveUps.containsKey(entry.getKey())
			// 			&& evaluated.containsKey(entry.getValue().genotype.pairedIndex))
			// 	.findFirst()
			// 	.map(entry -> entry.getValue());					
			// if (puzzleOpt.isPresent()) return puzzleOpt;
			// puzzleOpt = 
			// 	allListGroupedBySeenAndCount
			// 	.stream()
			// 	.filter(entry -> 
			// 		giveUps.containsKey(entry.getKey())
			// 			&& giveUps.containsKey(entry.getValue().genotype.pairedIndex))
			// 	.findFirst()
			// 	.map(entry -> entry.getValue());
			// if (puzzleOpt.isPresent()) return puzzleOpt;
			return allListGroupedBySeenAndCount.stream().findFirst().map(entry -> entry.getValue());
		};

	public static final SelectionPolicy dummy = 
		(studentId, available, evaluated, giveUps, logger) -> Optional.of(PuzzleEvaluation.dummy);
		
	public static final SelectionPolicy exploration = 
		(studentId, available, evaluated, giveUps, logger) -> 
		{
			Optional<PuzzleEvaluation> puzzleOpt = Utils.randFromMap(available);
			if (puzzleOpt.isPresent()) return puzzleOpt;
			else return Utils.randFromMap(giveUps);
		};

	public static final SelectionPolicy exploitation = 
		(studentId, available, evaluated, giveUps, logger) -> 
		{
			Optional<PuzzleEvaluation> puzzleOpt = 
				available.entrySet().stream()									
					.max(Comparator.comparing(g -> 
						(g.getValue().evaluations.size() == 0) 
							? 0 
							: (g.getValue().evaluations.entrySet().stream()
								.mapToDouble(entry -> entry.getValue().fitness)
								.sum() 
									/ g.getValue().evaluations.size())))
					.map(entry -> entry.getValue());	
			if (puzzleOpt.isPresent()) return puzzleOpt;
			else 
				return
					giveUps.entrySet().stream()									
						.max(Comparator.comparing(g -> 
							(g.getValue().evaluations.size() == 0) 
								? 0 
								: (g.getValue().evaluations.entrySet().stream()
									.mapToDouble(entry -> entry.getValue().fitness)
									.sum() 
										/ g.getValue().evaluations.size())))
						.map(entry -> entry.getValue());	
				
		};		
	public static final SelectionPolicy fatigue = 
		(studentId, available, evaluated, giveUps, logger) ->
			(InternalState.rand.nextDouble() <= InternalState.EPSILON)
			? exploration.select(studentId, available, evaluated, giveUps, logger)
			: exploitation.select(studentId, available, evaluated, giveUps, logger);

	public static final SelectionPolicy completeness = 
		(studentId, available, evaluated, giveUps, logger) -> 
		{
			Optional<PuzzleEvaluation> puzzleOpt = 
				available
					.entrySet().stream()
					.min(Comparator.comparing(entry -> 
							entry.getValue().evaluations.size()))
					.map(entry -> entry.getValue());
			if (puzzleOpt.isPresent()) return puzzleOpt;
			else 
				return
					giveUps
						.entrySet().stream()
						.min(Comparator.comparing(entry -> 
								entry.getValue().evaluations.size()))
						.map(entry -> entry.getValue());				
		};
}
