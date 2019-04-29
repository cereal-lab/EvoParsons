package evoparsons.broker;

import java.io.Serializable;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.DoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import evoparsons.repo.IRepo;
import evoparsons.rmishared.Auth;
import evoparsons.rmishared.ParsonsEvaluation;
import evoparsons.rmishared.ParsonsPuzzle;
import evoparsons.rmishared.Stats;

public class EvaluationDataStore
{		
	private static final String DEFAULT_STUDENTS_FILE = "students.bro";
	//private static final String DEFAULT_STUDENT_STAT_FILE = "studentStat.bro";
	private static final String DEFAULT_GENOTYPE_FILE = "genotype.bro";
	private String outputFolder;

	//private Map<String, Auth> students; //TODO: cen we remove it?
	private Map<String, Student> students;
	private Map<Integer, PuzzleEvaluation> genotypes;
	private Map<Integer, PuzzleEvaluation> currentGenerationGenotypes;
	//private String studentsFile;
	//private String studentStatsFile;
	private String genotypesFile;
	private int evalTries; 	
	private Log log;
	private Config config;
	private IRepo<String, Student> studentRepo;

	public EvaluationDataStore(Config config)
	{
		this.log = config.getLog();
		this.config = config;
		this.outputFolder = config.getOutputFolder();
		this.studentRepo = config.<String, Student>getRepo("evoparsons.repo.students");
		try {
			String evalTries = config.get("evoparsons.evalTries", "");
			this.evalTries = Integer.parseInt(evalTries);
		} catch (NumberFormatException e) {
			log.log("[EvaluationDataStore] Using default value for evalTries=2");
			this.evalTries = 2;
		}		
		//studentsFile = config.get("evoparsons.studentsFile", DEFAULT_STUDENTS_FILE);
		students = this.studentRepo.getAll();
			//Utils.<HashMap<String, Student>>loadFromFile(log, Paths.get(outputFolder, studentsFile).toString(), HashMap<String, Student>::new);
		if (students.size() == 0)
			log.log("[EvaluationDataStore] students map is empty");
		else 
		{
			log.log("[EvaluationDataStore] %d students were restored from %s", students.size(), this.studentRepo.getName());
			students.entrySet().stream()
				.sorted(Comparator.comparing(entry -> entry.getValue().getAuth().getSid()))
				.forEach(entry -> log.log("\t%s", entry.getKey()));
		}
		//studentStatsFile = config.get("evoparsons.studentsStatsFile", DEFAULT_STUDENT_STAT_FILE);
		//studentStats = Utils.<HashMap<Integer, Stats>>loadFromFile(log, Paths.get(outputFolder, studentStatsFile).toString(), HashMap<Integer, Stats>::new);
		genotypesFile = config.get("evoparsons.genotypesFile", DEFAULT_GENOTYPE_FILE);
		genotypes = 
			Utils.<Map<Integer, PuzzleEvaluation>>loadFromFile(log, Paths.get(outputFolder, genotypesFile).toString(), 
				HashMap<Integer, PuzzleEvaluation>::new);
		//FIX in timestamp field
		// genotypes
		// 	.entrySet()
		// 	.forEach(entry -> 
		// 		entry.getValue().evaluations
		// 			.entrySet()
		// 			.forEach(entry2 -> {
		// 				if (entry2.getValue().timestamp == 0) entry2.getValue().timestamp = Long.MIN_VALUE;
		// 			}));
		if (genotypes.size() == 0)
			log.log("[EvaluationDataStore] genotypes hash is empty");
		else
		{
			log.log("[EvaluationDataStore] %d genotypes were restored from %s", genotypes.size(), genotypesFile);
		}
		currentGenerationGenotypes = 
			genotypes.entrySet().stream()
				.collect(Collectors.groupingBy(entry -> entry.getValue().generation))
				.entrySet().stream()
				.max(Comparator.comparing(entry -> entry.getKey()))
				.map(entry -> entry.getValue())
				.orElse(new ArrayList<Entry<Integer, PuzzleEvaluation>>())
				.stream()
				.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
		if (currentGenerationGenotypes.size() > 0)
			log.log("[EvaluationDataStore] %d genotypes in current generation %d", currentGenerationGenotypes.size(), 
				currentGenerationGenotypes.entrySet().stream().findFirst().map(g -> g.getValue().generation).get());
	}

	public boolean hasStudents() { return students.size() > 0; }

	public void printInteractions(Library lib) {
		log.log("--------------------------------------------");
		String padding = new String(new char[14]).replace('\0', ' ');
		log.print(padding);
		genotypes.entrySet().stream()
			.sorted(Comparator.comparing(entry -> entry.getKey()))
			.forEach(genotype -> {		
				ParsonsPuzzle puzzle = genotype.getValue().genotype.getPuzzle(lib);	
				int programSize = puzzle.program.size();
				int appliedDistractorsCount = puzzle.distracters.size();	
				log.print("%10.10s",
					String.format(
						currentGenerationGenotypes.containsKey(genotype.getKey()) ? "%d,%d,%d*" : "%d,%d,%d", 
						genotype.getValue().genotype.genome[0],
						programSize,
						appliedDistractorsCount));
			});		
		log.log("");
		students.entrySet().stream()
			.sorted(Comparator.comparing(student -> student.getValue().getAuth().getSid()))
			.forEach(student -> 
				{
					log.print("%13.13s ", student.getKey());
					genotypes.entrySet().stream()
						.sorted(Comparator.comparing(entry -> entry.getKey()))
						.forEach(genotype -> {	
							if (!genotype.getValue().evaluations.containsKey(student.getKey())) log.print("%10.10s", "");
							else 
							{
								ParsonsEvaluation eval = genotype.getValue().evaluations.get(student.getKey());
								if (eval.gaveUp)
									log.print("%10.10s", "gaveUp");
								else 
									log.print("%10.10s", String.format("%d,%d,%.2f",
										eval.moves, eval.timeInMs / 1000, eval.fitness));
							}
						});
					log.log("");
				});		
		log.log("--------------------------------------------");	
	}

	public void addGenotypes(List<ParsonsGenotype> g, int generation)
	{
		currentGenerationGenotypes = 
			g.stream()
				.collect(Collectors.toMap(genotype -> genotype.getIndex(),
					genotype ->
					{
						PuzzleEvaluation evals = genotypes.get(genotype.getIndex());
						if (evals == null)
							return new PuzzleEvaluation(genotype, generation);
						else
							return new PuzzleEvaluation(genotype, evals.evaluations, generation);
					}));
		genotypes.putAll(currentGenerationGenotypes);
	}	   
		
	public ParsonsPuzzle getPuzzle(String sid, SelectionPolicy selectionPolicy, Library lib)
	{
		Optional<PuzzleEvaluation> genotypeOpt = 
			selectionPolicy.select(sid, currentGenerationGenotypes, log);
		if (!genotypeOpt.isPresent()) {
			log.err("[EvaluationDataStore.getPuzzle] Selection policy did not return puzzle. Check configuration");
			System.exit(1);		
		} else {
			log.log("[EvaluationDataStore.getPuzzle] student %s got %s", sid, genotypeOpt.get().genotype.toString());
		}
		return genotypeOpt.get().genotype.getPuzzle(lib);
	}

	public Stats getStudentStats(String sid) {
		Student student = students.get(sid);
		if (student == null)  return new Stats(0, 0);
		return student.getStats();
	}

	public void recordAttempt(String sid, String puzzleId) {
		Student student = students.get(sid);
		if (student != null) 
		{
			int attemptId = 
				student.getStats().attemptsPerPuzzle.getOrDefault(puzzleId, 0);
			student.getStats().attemptsPerPuzzle.put(puzzleId, attemptId + 1);
			saveStudent(student);
		}
	}	

	public Map<String, Stats> getStudentStats(String isig, List<String> ssigs) {
		if (isig == null || isig.isEmpty() || ssigs == null || ssigs.isEmpty()) return Collections.emptyMap();
		Set<String> ssigsSet = new HashSet<String>(ssigs);
		return 
			students.entrySet().stream()
				.filter(s -> ssigsSet.contains(s.getValue().getAuth().getSsig()) && s.getValue().getAuth().getSkey().equals(isig))
				.collect(Collectors.toMap(s -> s.getValue().getAuth().getSsig(), s -> s.getValue().getStats()));
	}

	public void addEvaluation(ParsonsEvaluation eval, SelectionPolicy selectionPolicy)
	{			
		PuzzleEvaluation puzzleEval = genotypes.get(eval.puzzleIndex);				
		if (puzzleEval != null)
		{
			ParsonsEvaluation existingEval = puzzleEval.evaluations.get(eval.sid);
			Student student = students.get(eval.sid);
			if (student == null) {
				//should not be here usually
				log.err("[EvaluationDataStore.addEvaluation] Cannot find student record for student %s. Ignoring", eval.sid);
				return;
			}
			if (student.getStats() == null) {
				//should not be here
				log.log("[EvaluationDataStore.addEvaluation] Cannot find stats for student %s. Ignoring", eval.sid);				
				return; 
			}
			student.getStats().duration += eval.timeInMs;			
			if ((existingEval == null) || 
				(existingEval.gaveUp && !eval.gaveUp))
			{	
				if (existingEval == null) student.getStats().puzzlesSeen++;
				if (!eval.gaveUp)
				{			
					final String sid = eval.sid;
					student.getStats().puzzlesSolved++;		
					int programIndex = puzzleEval.genotype.genome[0];
					boolean studentHasAlreadySeenPuzzle = 
						genotypes.entrySet().stream()
							.filter(entry -> 
								(entry.getValue().genotype.genome[0] == programIndex) 
								&& entry.getValue().evaluations.containsKey(sid)
								&& !entry.getValue().evaluations.get(sid).gaveUp)
							.findAny().isPresent();
					eval =
						studentHasAlreadySeenPuzzle ?
							new ParsonsEvaluation(eval.sid, eval.puzzleIndex, 
								eval.moves, eval.timeInMs,
								DoubleStream.concat(DoubleStream.of(eval.fitness),
									puzzleEval.evaluations.entrySet().stream()
										.filter(entry -> !entry.getValue().gaveUp)
										.mapToDouble(entry -> entry.getValue().fitness))
										.average()
										.getAsDouble(),							
								eval.gaveUp, eval.timestamp) : 
							eval;
				}		
				puzzleEval
					.evaluations
					.put(eval.sid, eval);				
			} else if (existingEval != null) {
				existingEval.setTimeStamp(eval.timestamp);
			}		
			saveStudent(student);	
		} else
			log.err("[EvaluationDataStore.addEvaluation] Cannot find genotype for eval %s", eval.toString());		
	}

	public Auth addStudent(String sid, String ssig, String skey)
	{
		Student student = 
			students.computeIfAbsent(sid, ignorable -> {
				log.log("[EvaluationDataStore.addStudent] %s, %s, %s", sid, ssig, skey);
				return new Student(new Auth(sid, ssig, skey), new Stats(0, 0));
			});		
		saveStudent(student);
		//student.auth.setSkey(skey);
		log.log("[EvaluationDataStore.addStudent] continue %s", sid);	
		return student.getAuth();
	}

	public ParsonsFitness getFitness(ParsonsEvaluation eval, Library lib)
	{	
		if (eval.gaveUp) return null;
		PuzzleEvaluation genotypeEvals = 
			currentGenerationGenotypes.get(eval.puzzleIndex);
		if (genotypeEvals == null)  // this evaluation is from previous generation
		{
			log.log("[EvaluationDataStore.getFitness] Eval %s, %d from prev. generation, ignored", eval.sid, eval.puzzleIndex);
			return null;
		}
				
		PuzzleEvaluation pairedGenotypeEvals = 
			currentGenerationGenotypes.get(genotypeEvals.genotype.getPairedIndex());			
		if (pairedGenotypeEvals == null) 
		{
			return null;
		}
			    
		class CommonEvaluations {
			public final List<Double> firstGenotypeEvals;
			public final List<Double> secondGenotypeEvals;
			public final List<String> students;
			public CommonEvaluations() {
				firstGenotypeEvals = new ArrayList<>();
				secondGenotypeEvals = new ArrayList<>();
				students = new ArrayList<>();
			}
		}

		DoubleFunction<Double> round = 	value -> Math.round(value * 1000) / 1000.0;			
		
		CommonEvaluations commonEvals = 
			genotypeEvals.evaluations.entrySet().stream()
				.filter(entry -> 
					!entry.getValue().gaveUp
					&& pairedGenotypeEvals.evaluations.containsKey(entry.getKey())
					&& !pairedGenotypeEvals.evaluations.get(entry.getKey()).gaveUp)
				.collect(
					() -> new CommonEvaluations(),
					(acc, entry) -> {
						acc.firstGenotypeEvals.add(round.apply(entry.getValue().fitness));
						acc.secondGenotypeEvals.add(round.apply(pairedGenotypeEvals.evaluations.get(entry.getKey()).fitness));
						acc.students.add(entry.getKey());
					},
					(acc1, acc2) -> {}
				);

		if (commonEvals.students.size() < evalTries) return null;

		log.log("[EvaluationDataStore.getFitness] (%d, %d) is ready%n%10.10s%s%n%10.10s%s%n%10.10s%s", 
			genotypeEvals.genotype.getIndex(), pairedGenotypeEvals.genotype.getIndex(),
			"students:",
			commonEvals.students.stream().map(i -> String.format("%6d", i)).collect(Collectors.joining(" ")),
			"first:",
			commonEvals.firstGenotypeEvals.stream().map(d -> String.format("%6.2f", d)).collect(Collectors.joining(" ")),
			"second:",
			commonEvals.secondGenotypeEvals.stream().map(d -> String.format("%6.2f", d)).collect(Collectors.joining(" "))
			);		
		return ParsonsFitness.Create(genotypeEvals.genotype, commonEvals.firstGenotypeEvals.stream().mapToDouble(x -> x).toArray(), 
			pairedGenotypeEvals.genotype, commonEvals.secondGenotypeEvals.stream().mapToDouble(x -> x).toArray());				
	}

	private void saveStudent(Student student)
	{
		try 
		{
			this.studentRepo.update(students.entrySet().stream().map(kv -> kv.getValue()).collect(Collectors.toList()));
		} catch (Exception e) {
			this.log.err("[EvalDataStore] Cannot save student: %s", student.getAuth().getSid());
			this.log.err(e.getMessage());
		}
	}	

	public void saveGenotypes()
	{
		if (!genotypes.isEmpty())
		{				
			Utils.saveToFile(log, genotypes, Paths.get(outputFolder, genotypesFile).toString());
		}
		else
			log.log("[EvaluationDataStore.saveGenotypes] Genotypes database is empty!");
	}	

	// public Set<String> getSsigsForISig(String isig) {
	// 	return 
	// 		students.entrySet().stream().filter(entry -> entry.getValue().auth.getSkey().equals(isig))
	// 			.map(entry -> entry.getValue().auth.ssig)
	// 			.collect(Collectors.toSet());
	// }


}
