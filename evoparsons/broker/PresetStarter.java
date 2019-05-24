package evoparsons.broker;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PresetStarter implements EAStarter {    

    private List<ParsonsGenotype> genotypes;
    private BrokerEAInterface broker;
    private Log log;

    private ParsonsGenotype createGenotypeFromString(Library lib, List<String> phenotypeComponents) {
        String programName = phenotypeComponents.get(0);
        Program p;
        try 
        {
            p = lib.getProgram(Integer.parseInt(programName));
        } catch (NumberFormatException e) {
            p = lib.getProgram(programName);
        }
        if (p == null) {
            log.err("Could not find program %s in library", programName);
            System.exit(1);
        }
        List<Integer> transformIndexss =
            phenotypeComponents.stream().skip(1).map(name -> {
                Transform t;
                try 
                {
                    t = lib.getTransform(Integer.parseInt(name));
                } catch (NumberFormatException e) 
                {
                    t = lib.getTransform(name);
                }
                if (t == null) {
                    log.err("Could not find transform %s in library", name);
                    System.exit(1);    
                }
                return Integer.valueOf(t.getIndex());
            }).collect(Collectors.toList());
        List<Integer> gs = new ArrayList<Integer>();
        gs.add(p.getIndex());
        gs.addAll(transformIndexss);
        return new ParsonsGenotype(genotypes.size(), gs.stream().mapToInt(i -> i).toArray());
    }

    public PresetStarter(Config config, BrokerEAInterface broker) {
        this.broker = broker;
        Library lib = broker.getLib();
        this.log = config.getLog();
        genotypes = new ArrayList<>();
        Object presetConfig = config.getObject("ea.preset");
        if (presetConfig instanceof String)
        {
            String presetUrl = (String)presetConfig;
            try(InputStream stream = new URL(presetUrl).openStream();
                Scanner s = new Scanner(stream)) {
                while (s.hasNext())
                {
                    List<String> phenotypeComponents = 
                        Arrays.stream(s.nextLine().split(","))
                            .map(str -> str.trim()).collect(Collectors.toList());                    
                    genotypes.add(createGenotypeFromString(lib, phenotypeComponents));
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
                    genotypes.add(createGenotypeFromString(lib, line)));
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