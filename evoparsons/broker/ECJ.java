package evoparsons.broker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

import ec.EvolutionState;
import ec.Evolve;
import ec.util.Output;
import ec.util.Parameter;
import ec.util.ParameterDatabase;
import evoparsons.ecj.ParsonsEvolutionState;

public class ECJ implements EvolutionAlgorithm {    
    private static final String GENOTYPE_EVOL_FILE = "genotypeEvol.bro";
    ParsonsEvolutionState evolState;
    private Config config;
    private Broker broker;
    private Log log;
    private int programCount;
    private int transformCount;

    public ECJ(Log log, Config config, Broker broker, int programCount, int transformCount) {
        this.config = config;
        this.broker = broker;
        this.log = log;
        this.programCount = programCount;
        this.transformCount = transformCount;
    }

	@Override
	public void runFresh(String config) {        
        File ecjConfigFile = Paths.get(config).toFile().getAbsoluteFile();
        ParameterDatabase params = null;		
        try {
            params = 
                new ParameterDatabase(ecjConfigFile, 
                        new String[] { "-file", ecjConfigFile.getCanonicalPath() });
            params.set(new Parameter("pop.subpop.0.species.min-gene"), "0");
            params.set(new Parameter("pop.subpop.0.species.max-gene"), String.valueOf(transformCount - 1));
            params.set(new Parameter("pop.subpop.0.species.min-gene.0"), "0");
            params.set(new Parameter("pop.subpop.0.species.max-gene.0"), String.valueOf(programCount - 1));
        } catch (IOException e) {
            log.err("[ECJ.runFresh] Error starting ECJ (check param file): %s", e.getMessage());
            System.exit(1);
        }
        Output out = Evolve.buildOutput();
        evolState = (ParsonsEvolutionState)Evolve.initialize(params, 0, out);
        evolState.setEvolutionAlgorithm(this);
        evolState.setGenotypeEvolutionRecorder(createGenotypeEvolutionRecorder());
        evolState.run(EvolutionState.C_STARTED_FRESH);
	}

	@Override
	public void runCheckpoint(String config) {
        evolState = null;

        File[] checkpoints = 
            Paths.get(this.config.getOutputFolder()).toFile().listFiles((dir, name) -> name.endsWith(".gz"));
    
        Optional<File> oldestCheckpoint = 						
            Arrays.stream(checkpoints).max((file1, file2) -> 
                {
                    String[] file1Parts = file1.getName().split("\\.");
                    String[] file2Parts = file2.getName().split("\\.");
                    String file1IndexStr = file1Parts[file1Parts.length - 2];
                    String file2IndexStr = file2Parts[file2Parts.length - 2];
                    int priorityOfFirst = -1;
                    int priorityOfSecond = -1;
                    try {
                        priorityOfFirst = Integer.parseInt(file1IndexStr);
                        try {
                            priorityOfSecond = Integer.parseInt(file2IndexStr);
                            return Integer.compare(priorityOfFirst, priorityOfSecond);
                        } catch (NumberFormatException e) {
                            log.log("[ECJ.runCheckpoint] Ignoring file %s as checkpoint file. Format should be <somethig>.X.gz", file2.getName());
                            return 1;										
                        }
                    } catch (NumberFormatException e) {
                        log.log("[ECJ.runCheckpoint] Ignoring file %s as checkpoint file. Format should be <somethig>.X.gz", file1.getName());
                        return -1;
                    }
                }
            );
        if (oldestCheckpoint.isPresent()) {
            evolState = (ParsonsEvolutionState)Evolve.possiblyRestoreFromCheckpoint(
                                                new String[] { "-checkpoint", oldestCheckpoint.get().getPath() });	
        }
		if (evolState != null)
		{
            evolState.setEvolutionAlgorithm(this);
            evolState.setGenotypeEvolutionRecorder(createGenotypeEvolutionRecorder());
			evolState.run(EvolutionState.C_STARTED_FROM_CHECKPOINT);
		} else {
            log.log("[ECJ.runCheckpoint] Cannot restore from checkpoint. Start fresh.");
            this.runFresh(config);
        }        
    }
    
	@Override
	public void setFitness(ParsonsFitness fitness) {
		this.evolState.setFitness(fitness);
    }          

	private BiConsumer<ParsonsGenotype, ParsonsGenotype> createGenotypeEvolutionRecorder() {
        Map<Integer, List<ParsonsGenotype>> genotypeEvolution;
		genotypeEvolution =
			Utils.<Map<Integer, List<ParsonsGenotype>>>loadFromFile(log, Paths.get(config.getOutputFolder(), GENOTYPE_EVOL_FILE).toString(), 
				HashMap<Integer, List<ParsonsGenotype>>::new);
		if (genotypeEvolution.size() == 0)
			log.log("[ECJ.createGenotypeEvolutionRecorder] genotype evolution map is empty");
		else 
            log.log("[ECJ.createGenotypeEvolutionRecorder] %d evolution chains were srestored from %s", new Object[] { genotypeEvolution.size(), GENOTYPE_EVOL_FILE });				
        
        return 
            (before, after) -> 
            {
                List<ParsonsGenotype> genotypeHistory = genotypeEvolution.remove(before.getIndex());
                if (genotypeHistory == null) {
                    genotypeHistory = new LinkedList<>();
                    genotypeHistory.add(before);
                    genotypeHistory.add(after);
                    genotypeEvolution.put(after.getIndex(), genotypeHistory);
                } else {
                    genotypeHistory.add(after);
                    genotypeEvolution.put(after.getIndex(), genotypeHistory);
                }
                Utils.saveToFile(log, genotypeEvolution, Paths.get(config.getOutputFolder(), GENOTYPE_EVOL_FILE).toString());
            };
	}

	@Override
	public Broker getBroker() {
		return broker;
	}    
}