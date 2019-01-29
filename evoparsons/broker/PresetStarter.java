package evoparsons.broker;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PresetStarter implements EAStarter {    

    private String presetUrl;
    private List<ParsonsGenotype> genotypes;
    private BrokerEAInterface broker;
    private Log log;

    public PresetStarter(Log log, Config config, BrokerEAInterface broker) {
        this.broker = broker;
        this.log = log;
        genotypes = new ArrayList<>();
        presetUrl = config.get("evoparsons.ea.preset", "");
        try(InputStream stream = new URL(presetUrl).openStream();
            Scanner s = new Scanner(stream)) {
            while (s.hasNext())
            {
                String line = s.next();
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
    }
	@Override
	public void runFresh(String config) {     
        broker.setGenotypes(genotypes, 0);
        log.log("[PresetStarter.runFresh] Waiting for incoming users!");
	}

	@Override
	public void runCheckpoint(String config) {
        //do nothing here
        runFresh(config);
    }
        
}