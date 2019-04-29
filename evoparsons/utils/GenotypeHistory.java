package evoparsons.utils;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import evoparsons.broker.Config;
import evoparsons.broker.Log;
import evoparsons.broker.ParsonsGenotype;
import evoparsons.broker.Utils;

public class GenotypeHistory {
    public static final String GENOTYPE_EVOL_FILE = "genotypeEvol.bro";
    public static void main(String[] args) throws FileNotFoundException {
        Log log = Log.file("genotypesHistory.txt");
        Config config = new Config(null, null) {{
            outputFolder = args[0];
        }};
		Map<Integer, List<ParsonsGenotype>> genotypeEvolution =
			Utils.<Map<Integer, List<ParsonsGenotype>>>loadFromFile(log, Paths.get(config.getOutputFolder(), GENOTYPE_EVOL_FILE).toString(), 
                HashMap<Integer, List<ParsonsGenotype>>::new);
        log.log("Chains of genotypes: %d", genotypeEvolution.size());
        for (List<ParsonsGenotype> chain: genotypeEvolution.values())
        {
            log.log("%s", chain.stream().map(g -> g.toString()).collect(Collectors.joining(" --> ")));
        }
    }
}