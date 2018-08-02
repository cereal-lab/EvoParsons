package evoparsons.broker;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Preset implements EvolutionAlgorithm {    

    private List<ParsonsGenotype> genotypes;
    private Broker broker;
    private Log log;

    public Preset(Log log, Config config, Broker broker, ParsonsGenotypeIndex genotypeStore) {
        this.broker = broker;
        this.log = log;
        genotypes = new ArrayList<>();
        try(InputStream stream = new URL(config.getPresetUrl()).openStream();
            Scanner s = new Scanner(stream)) {
            while (s.hasNext())
            {
                String line = s.next();
                genotypes
                    .add(new ParsonsGenotype(genotypeStore, 
                        Arrays.stream(line.split(","))
                            .map(str -> str.trim())
                            .mapToInt(Integer::valueOf)));
            }
            log.log("[Preset] Genotypes were loaded from %s", config.getPresetUrl());
        } catch (Exception e) {
            log.err("[Preset] Cannot read presets from %s", config.getPresetUrl());
            e.printStackTrace();
            System.exit(1);
        }		
    }
	@Override
	public void runFresh(String config) {     
        broker.setGenotypes(genotypes, 0);
        log.log("[Preset.runFresh] Waiting for incoming users!");
	}

	@Override
	public void runCheckpoint(String config) {
        //do nothing here
        runFresh(config);
    }
    
	@Override
	public void setFitness(ParsonsFitness fitness) {
        //do nothing here 
    }
	@Override
	public Broker getBroker() {
		return broker;
	}      
    
}