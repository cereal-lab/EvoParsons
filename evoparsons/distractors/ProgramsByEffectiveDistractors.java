package evoparsons.distractors;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import evoparsons.broker.Config;
import evoparsons.broker.Log;
import evoparsons.broker.ParsonsLibrary;

public class ProgramsByEffectiveDistractors {
    public static void main(String[] args) throws IOException {
        //Log log = Log.console;
        try (FileWriter writer = new FileWriter(new File("matrix.txt")))
        {
        Log log = new Log() {
            @Override
            public void log(String format, Object... params){
                this.print(format + "%n", params);
            }

			@Override
			public void err(String format, Object... params) {
                Log.console.err(format, params);
			}

			@Override
			public void print(String format, Object... params) {
                try {
                    writer.write(String.format(format, params));
                } catch (Exception e) {
                    e.printStackTrace();
                    System.exit(1);
                }				
			}
        };
        Config config = new Config(Log.console) {{
            programFolder = args[0];
            transformsFolder = args[1];
        }};
        ParsonsLibrary lib = new ParsonsLibrary(Log.console, config);

        class Tuple<A, B> {
            public final A a;
            public final B b;
            public Tuple(A a, B b) { this.a = a; this.b = b; }
        }
        Map<String, Map<String, int[]>> programsToTransforms = 
            lib.programStream()
                .map(program ->
                    {
                        Map<String, int[]> transforms = 
                            lib.transformStream()
                            .map(transform -> {
                                int[] lines = 
                                    IntStream.range(0, program.body.size())
                                        .mapToObj(i -> new Tuple<>(i, program.body.get(i)))
                                        .filter(line -> {
                                            Matcher matcher = transform.getPattern().matcher(line.b);
                                            String distractedLine = matcher.replaceFirst(transform.getTransform());
                                            return matcher.matches() && !distractedLine.equals(line.b);
                                        })
                                        .mapToInt(line -> line.a)
                                        .toArray();
                                return new Tuple<>(transform.getFileName(), lines);
                                })
                            .filter(transformWithAppl -> transformWithAppl.b.length > 0)
                            .collect(Collectors.toMap(t -> t.a, t -> t.b));                        
                        return new Tuple<>(program.fileName, transforms);                   
                    })
                .collect(Collectors.toMap(p -> p.a, p -> p.b));
        Map<String, Map<String, int[]>> transformsToPrograms = 
            programsToTransforms
                .entrySet()
                .stream()
                .flatMap(e -> 
                    e.getValue()
                        .entrySet()
                        .stream()
                        .map(e2 -> 
                            {
                                Tuple<String, int[]> t = new Tuple<String, int[]>(e.getKey(), e2.getValue());
                                return new Tuple<String, Tuple<String, int[]>>(e2.getKey(), t);
                            }))
                .collect(Collectors.groupingBy(e -> e.a, Collectors.toMap(e2 -> e2.b.a, e2 -> e2.b.b)));
                //         e.b.stream()
                //             .map(e2 -> e2.b)
                //             .collect(Collectors.groupingBy(e2 -> e2.a, 
                //                 e2 -> e2.stream().map(e3 -> e3.b)
                //             ))
                // ));        
        log.log("-----------------------------");
        log.log("-----------------------------");
        log.print("%25.25s", "");
        List<Tuple<String, Map<String, int[]>>> headers = 
            lib.transformStream()
                .map(p -> new Tuple<>(p.getFileName(), transformsToPrograms.getOrDefault(p.getFileName(), Collections.emptyMap())))
                .sorted(Comparator.comparing(e -> -e.b.size()))
                .collect(Collectors.toList());
        headers.stream()
            .forEach(h -> log.print("%-12.12s  ", String.format("%s,%d", h.a.split("\\.")[0], h.b.size())));
        log.log("");
        programsToTransforms
            .entrySet()
            .stream()
            .sorted(Comparator.comparing(e -> -e.getValue().size()))
            .forEach(e -> {
                log.print("%20.20s,%2d  ", e.getKey().split("\\.")[0], e.getValue().size());
                headers
                    .stream()
                    .forEach(h -> {
                        int[] ln = e.getValue().getOrDefault(h.a, new int[0]);
                        String lines = 
                            Arrays.stream(ln)
                                .mapToObj(String::valueOf)
                                .collect(Collectors.joining(","));                        
                        if (ln.length > 0)
                            log.print("%-12.12s  ", String.format("[%d]%s", ln.length, lines));
                        else log.print("%-12.12s  ", "");
                    });
                log.log("");
            });
        }
    }
}