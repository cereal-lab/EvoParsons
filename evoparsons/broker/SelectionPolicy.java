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
	public Optional<PuzzleEvaluation> select(String sid, 
		Map<Integer, PuzzleEvaluation> available, 
		Map<Integer, PuzzleEvaluation> evaluated, 
		Map<Integer, PuzzleEvaluation> giveUps, 
		Log logger);
		
	public default Optional<PuzzleEvaluation> select(String sid, Map<Integer, PuzzleEvaluation> currentGeneration, Log logger) {
		class Tmp {
			public final Map<Integer, PuzzleEvaluation> available = new HashMap<>();
			public final Map<Integer, PuzzleEvaluation> evaluated = new HashMap<>();
			public final Map<Integer, PuzzleEvaluation> giveUps = new HashMap<>();
		}
		Tmp tmp =
			currentGeneration
				.entrySet()
				.stream()
				.collect(() -> new Tmp(), 
					(acc, entry) -> {
						if (entry.getValue().evaluations.containsKey(sid)) {
							if (entry.getValue().evaluations.get(sid).gaveUp)
								acc.giveUps.put(entry.getKey(), entry.getValue());
							else 
								acc.evaluated.put(entry.getKey(), entry.getValue());
						} else 
							acc.available.put(entry.getKey(), entry.getValue());
					},
					(acc1, acc2) -> {}
				);
		Optional<PuzzleEvaluation> selectedPuzzle = 
			this.select(sid, tmp.available, tmp.evaluated, tmp.giveUps, logger);
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
				(sid, avaliable, evaluated, giveUps, logger) -> 
				{
					Optional<PuzzleEvaluation> puzzleOpt = 
						that.select(sid, avaliable, evaluated, giveUps, logger);
					if (puzzleOpt.isPresent()) return puzzleOpt;
					else 
						return 		
							(evaluated.size() >= limit) 
								? nextPolicy.select(sid, avaliable, evaluated, giveUps, logger)
								: Optional.empty();
				};
		}
	}

	public static final SelectionPolicy cycling = 
		(sid, available, evaluated, giveUps, logger) ->
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
		final SelectionPolicy that = this;
		return 
			new LimitedSelectionPolicy(n) {
				@Override 
				public Optional<PuzzleEvaluation> select(String sid, Map<Integer, PuzzleEvaluation> avaliable, Map<Integer, PuzzleEvaluation> evaluated, Map<Integer, PuzzleEvaluation> giveUps, Log logger)
				{					
					if (evaluated.size() < limit) {
						return that.select(sid, avaliable, evaluated, giveUps, logger);
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
		(sid, available, evaluated, giveUps, logger) -> {
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
							ParsonsEvaluation eval = keyValue.getValue().evaluations.get(sid);
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
			return allListGroupedBySeenAndCount.stream().findFirst().map(entry -> entry.getValue());
		};

	public static final SelectionPolicy dummy = 
		(sid, available, evaluated, giveUps, logger) -> Optional.of(PuzzleEvaluation.dummy);
		
	public static final SelectionPolicy exploration = 
		(sid, available, evaluated, giveUps, logger) -> 
		{
			Optional<PuzzleEvaluation> puzzleOpt = Utils.randFromMap(available);
			if (puzzleOpt.isPresent()) return puzzleOpt;
			else return Utils.randFromMap(giveUps);
		};

	public static final SelectionPolicy exploitation = 
		(sid, available, evaluated, giveUps, logger) -> 
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
		(sid, available, evaluated, giveUps, logger) ->
			(InternalState.rand.nextDouble() <= InternalState.EPSILON)
			? exploration.select(sid, available, evaluated, giveUps, logger)
			: exploitation.select(sid, available, evaluated, giveUps, logger);

	public static final SelectionPolicy completeness = 
		(sid, available, evaluated, giveUps, logger) -> 
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
