package evoparsons.utils;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import evoparsons.broker.Config;
import evoparsons.broker.Log;
import evoparsons.broker.ParsonsLibrary;
import evoparsons.broker.PuzzleEvaluation;
import evoparsons.broker.Utils;
import evoparsons.rmishared.ParsonsEvaluation;
import evoparsons.rmishared.ParsonsPuzzle;

public class InteractionMatrix {
    public static void main(String[] args) {
        Config config = new Config(Log.console) {{
            outputFolder = args[0];
        }};
        Log log = Log.console;
		Map<Integer, PuzzleEvaluation> genotypes = 
			Utils.<Map<Integer, PuzzleEvaluation>>loadFromFile(log, Paths.get(config.getOutputFolder(), "genotypes.bro").toString(), 
                HashMap<Integer, PuzzleEvaluation>::new);    
                
        HashMap<String, Integer> students = Utils.<HashMap<String, Integer>>loadFromFile(log, Paths.get(config.getOutputFolder(), "students.bro").toString(), HashMap<String, Integer>::new);                
                
        ParsonsLibrary lib = new ParsonsLibrary(Log.console, args[1], args[2]);


		Map<Integer, PuzzleEvaluation> currentGenerationGenotypes = 
			genotypes.entrySet().stream()
				.collect(Collectors.groupingBy(entry -> entry.getValue().generation))
				.entrySet().stream()
				.max(Comparator.comparing(entry -> entry.getKey()))
				.map(entry -> entry.getValue())
				.orElse(new ArrayList<Entry<Integer, PuzzleEvaluation>>())
				.stream()
				.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));

                
        log.log("--------------------------------------------");
        String padding = new String(new char[13]).replace('\0', ' ');
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
                        genotype.getValue().genotype.getGenome()[0],
                        programSize,
                        appliedDistractorsCount));
            });		
        log.log("");
        students.entrySet().stream()
            .sorted(Comparator.comparing(student -> student.getValue()))
            .forEach(student -> 
                {
                    log.print("%13.13s", String.format("%8.8s[%d]", student.getKey(), student.getValue()));
                    genotypes.entrySet().stream()
                        .sorted(Comparator.comparing(entry -> entry.getKey()))
                        .forEach(genotype -> {	
                            ParsonsPuzzle puzzle = genotype.getValue().genotype.getPuzzle(lib);
                            int phenotypeSize = puzzle.program.size() + puzzle.distracters.size();															
                            if (!genotype.getValue().evaluations.containsKey(student.getValue())) log.print("%10.10s", "");
                            else 
                            {
                                ParsonsEvaluation eval = genotype.getValue().evaluations.get(student.getValue());
                                if (eval.gaveUp)
                                    log.print("%10.10s", "gaveUp");
                                else 
                                    log.print("%10.10s", String.format("%.1f,%.2f",
                                        eval.fitness, eval.fitness / phenotypeSize));
                            }
                        });
                    log.log("");
                });		
        log.log("--------------------------------------------");	                        

        //EvaluationDataStore store = new EvaluationDataStore(Log.console, config);        
        //store.printInteractions(lib);
    }
}