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

class BroToCsv {
    public static void main(String[] args) {
        Map<String, Integer> students = Utils.<HashMap<String, Integer>>loadFromFile(Log.console, args[0], HashMap<String, Integer>::new);
		Map<Integer, PuzzleEvaluation> genotypes = 
            Utils.<Map<Integer, PuzzleEvaluation>>loadFromFile(Log.console, args[1], HashMap<Integer, PuzzleEvaluation>::new);        
        Map<Integer, List<ParsonsEvaluation>> studentEvalCount = 
            genotypes.entrySet()
                .stream()
                .flatMap(kv -> kv.getValue().evaluations.entrySet().stream())
                .collect(Collectors.groupingBy(kv -> kv.getKey(), Collectors.mapping(kv -> kv.getValue(), Collectors.toList())));
        System.out.println();
        students.entrySet()
            .stream()
            .filter(kv -> studentEvalCount.containsKey(kv.getValue()))
            .sorted(Comparator.comparing(kv -> kv.getValue()))
            .forEach(kv -> {
                long totalCnt = studentEvalCount.get(kv.getValue()).size();
                long solved = studentEvalCount.get(kv.getValue()).stream().filter(p -> !p.gaveUp).count();
                System.out.format("%d,%s,%d,%d,%d%n", kv.getValue(), kv.getKey(), totalCnt, solved, totalCnt - solved);
            });
    }
}