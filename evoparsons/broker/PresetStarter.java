package evoparsons.broker;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PresetStarter implements EAStarter {    

    private List<ParsonsGenotype> genotypes;
    private BrokerEAInterface broker;
    private Log log;

    public PresetStarter(Config config, BrokerEAInterface broker) {
        this.broker = broker;
        this.log = config.getLog();
        genotypes = new ArrayList<>();
        Object presetConfig = config.getObject("evoparsons.ea.preset");
        if (presetConfig instanceof String)
        {
            String presetUrl = (String)presetConfig;
            try(InputStream stream = new URL(presetUrl).openStream();
                Scanner s = new Scanner(stream)) {
                while (s.hasNext())
                {
                    String line = s.nextLine();
                    genotypes
                        .add(new ParsonsGenotype(genotypes.size(), 
                        Arrays.stream(line.split(","))
                            .map(str -> str.trim())
                            .mapToInt(Integer::valueOf).toArray()));
                }
                log.log("[Preset] Genotypes were loaded from %s", presetUrl);
            } catch (Exception e) {
                log.err("[Preset] Cannot read presets from %s", presetUrl);
                e.printStackTrace();
                System.exit(1);
            }
        } else if (presetConfig instanceof List)		
        {
            List<List<String>> c = (List<List<String>>)presetConfig;
            c.stream()
                .forEach(line -> 
                    genotypes
                        .add(new ParsonsGenotype(genotypes.size(), 
                        line.stream()
                            .map(str -> str.trim())
                            .mapToInt(Integer::valueOf).toArray()))                
                );
        } else {
            log.err("[Preset] Unsupported config %s", presetConfig.toString());
            System.exit(1);            
        }
    }
	@Override
	public void runFresh() {     
        broker.setGenotypes(genotypes, 0);
        log.log("[PresetStarter.runFresh] Waiting for incoming users!");
	}

	@Override
	public void runCheckpoint() {
        //do nothing here
        runFresh();
    }
        
}