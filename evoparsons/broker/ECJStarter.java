package evoparsons.broker;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

import ec.EvolutionState;
import ec.Evolve;
import ec.util.Output;
import ec.util.Parameter;
import ec.util.ParameterDatabase;
import evoparsons.ecj.ParsonsEvolutionState;

public class ECJStarter implements EAStarter {    
    ParsonsEvolutionState evolState;
    private Config config;
    private BrokerEAInterface broker;
    public final Log log;

    public ECJStarter(Log log, Config config, BrokerEAInterface broker) {
        this.config = config;
        this.log = log;
        this.broker = broker;
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
            params.set(new Parameter("pop.subpop.0.species.max-gene"), String.valueOf(broker.getLib().getTransformCount() - 1));
            params.set(new Parameter("pop.subpop.0.species.min-gene.0"), "0");
            params.set(new Parameter("pop.subpop.0.species.max-gene.0"), String.valueOf(broker.getLib().getProgramCount() - 1));
        } catch (IOException e) {
            log.err("[ECJ.runFresh] Error starting ECJ (check param file): %s", e.getMessage());
            System.exit(1);
        }
        Output out = Evolve.buildOutput();
        this.evolState = 
            ((ParsonsEvolutionState)Evolve.initialize(params, 0, out))
                .withConfig(log, this.config)
                .withGenotypeFactory(new ParsonsGenotypeIndex(log, this.config))
                .withBroker(this.broker)
                .start(EvolutionState.C_STARTED_FRESH);
	}

	@Override
	public void runCheckpoint(String config) {
        evolState = null;

        File[] checkpoints = 
            Paths.get(this.config.getOutputFolder(log)).toFile().listFiles((dir, name) -> name.endsWith(".gz"));
    
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
            evolState
                .withConfig(log, this.config)
                .withGenotypeFactory(new ParsonsGenotypeIndex(log, this.config))
                .withBroker(broker)
			    .start(EvolutionState.C_STARTED_FROM_CHECKPOINT);
		} else {
            log.log("[ECJ.runCheckpoint] Cannot restore from checkpoint. Start fresh.");
            this.runFresh(config);
        }        
    }       

}