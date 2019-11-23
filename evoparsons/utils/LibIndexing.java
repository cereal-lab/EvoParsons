package evoparsons.utils;

import java.util.stream.Stream;

import evoparsons.broker.Config;
import evoparsons.broker.Library;
import evoparsons.broker.ParsonsLibrary;
import evoparsons.broker.Program;
import evoparsons.broker.Transform;

public class LibIndexing {
    public static void main(String[] args) {
        Config config = new Config(null) {{
            this.props.put("output", args[0]);
            this.props.put("programs", args[1]);
            this.props.put("transforms", args[2]);
        }};

        ParsonsLibrary lib = new ParsonsLibrary(config);

        
        // System.out.println("----------------------PROGRAMS-------------------------");
        // for (int i = 0; i < lib.getProgramCount(); i++) {
        //     System.out.printf("%d\t%s%n", i, lib.getProgram(i).fileName);
        // }
        // System.out.println("----------------------TRANSFORMS-------------------------");
        // for (int i = 0; i < lib.getTransformCount(); i++) {
        //     System.out.printf("%d\t%s%n", i, lib.getTransform(i).getFileName());
        // }        
    }
}