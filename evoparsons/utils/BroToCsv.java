package evoparsons.utils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import evoparsons.broker.Log;
import evoparsons.broker.Utils;
import evoparsons.rmishared.ParsonsEvaluation;
import evoparsons.broker.PuzzleEvaluation;
import evoparsons.broker.Student;

class BroToCsv {
    public static void main(String[] args) {
        Map<String, Student> students = Utils.<HashMap<String, Student>>loadFromFile(Log.console, args[0], HashMap<String, Student>::new);
		Map<Integer, PuzzleEvaluation> genotypes = 
            Utils.<Map<Integer, PuzzleEvaluation>>loadFromFile(Log.console, args[1], HashMap<Integer, PuzzleEvaluation>::new);        
        Map<String, List<ParsonsEvaluation>> studentEvalCount = 
            genotypes.entrySet()
                .stream()
                .flatMap(kv -> kv.getValue().evaluations.entrySet().stream())
                .collect(Collectors.groupingBy(kv -> kv.getKey(), Collectors.mapping(kv -> kv.getValue(), Collectors.toList())));
        System.out.println();
        students.entrySet()
            .stream()
            .filter(kv -> studentEvalCount.containsKey(kv.getValue().auth.sid))
            .sorted(Comparator.comparing(kv -> kv.getValue().auth.sid))
            .forEach(kv -> {
                long totalCnt = studentEvalCount.get(kv.getValue().auth.sid).size();
                long solved = studentEvalCount.get(kv.getValue().auth.sid).stream().filter(p -> !p.gaveUp).count();
                System.out.format("%s,%s,%d,%d,%d%n", kv.getValue().auth.sid, kv.getKey(), totalCnt, solved, totalCnt - solved);
            });
    }
}