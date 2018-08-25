package evoparsons.utils;

import evoparsons.broker.Config;
import evoparsons.broker.EvaluationDataStore;
import evoparsons.broker.Log;
import evoparsons.broker.ParsonsLibrary;

public class InteractionMatrix {
    public static void main(String[] args) {
        Config config = new Config(Log.console) {{
            outputFolder = "/home/dvitel/usf/sw/2018-summer";
        }};
        EvaluationDataStore store = new EvaluationDataStore(Log.console, config);
        ParsonsLibrary lib = new ParsonsLibrary(Log.console, "/home/dvitel/usf/sw/evo/ParsonsLibraries/Programs/Java", "/home/dvitel/usf/sw/evo/ParsonsLibraries/Transforms/Java");
        store.printInteractions(lib);
    }
}