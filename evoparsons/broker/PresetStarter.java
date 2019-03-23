package evoparsons.broker;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PresetStarter implements EAStarter {    

    private String presetUrl;
    private List<ParsonsGenotype> genotypes;
    private BrokerEAInterface broker;
    private Log log;

    public PresetStarter(Config config, BrokerEAInterface broker) {
        this.broker = broker;
        Library lib = broker.getLib();
        this.log = config.getLog();
        genotypes = new ArrayList<>();
        presetUrl = config.get("evoparsons.ea.preset", "");
        try(InputStream stream = new URL(presetUrl).openStream();
            Scanner s = new Scanner(stream)) {
            while (s.hasNext())
            {
                String line = s.nextLine();
                List<String> phenotypeComponents = 
                    Arrays.stream(line.split(","))
                        .map(str -> str.trim()).collect(Collectors.toList());
                String programName = phenotypeComponents.get(0);
                Program p = lib.getProgram(programName);
                if (p == null) {
                    log.err("Could not find program %s in library", programName);
                    System.exit(1);
                }
                List<Integer> transformIndexss =
                    phenotypeComponents.stream().skip(1).map(name -> {
                        Transform t = lib.getTransform(name);
                        if (t == null) {
                            log.err("Could not find transform %s in library", name);
                            System.exit(1);    
                        }
                        return Integer.valueOf(t.getIndex());
                    }).collect(Collectors.toList());
                List<Integer> gs = new ArrayList<Integer>();
                gs.add(p.getIndex());
                gs.addAll(transformIndexss);
                genotypes
                    .add(new ParsonsGenotype(genotypes.size(), gs.stream().mapToInt(i -> i).toArray()));
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