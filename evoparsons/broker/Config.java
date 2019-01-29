package evoparsons.broker;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class Config {
    //Reflection - creating instance
    // 1. static field
    // 2. constructor
    // 3. factory method
    @SuppressWarnings("unchecked")
    public <T> T getInstance(Log log, String instancePathKey, Object... params) {
        String instancePath = get(instancePathKey, "");
        String[] parts = instancePath.split("\\+");
        try {
            if (parts.length == 1) {
                //assuming class name was provided
                if (params == null) params = new Object[0];
                Class<?>[] paramClasses = 
                    Arrays.stream(params).map(p -> p.getClass())
                        .collect(Collectors.toList())
                        .toArray(new Class<?>[0]);
                return (T)Class.forName(instancePath).getConstructor(paramClasses).newInstance(params);

            } else {
                //static field was provided
                String staticField = parts[parts.length - 1];
                
                Class<?> cls = Class.forName(String.join("+", Arrays.copyOfRange(parts, 0, parts.length - 1)));
                try 
                {
                    Field f = cls.getDeclaredField(staticField);
                    return (T)f.get(null);
                } catch (NoSuchFieldException e)
                {
                    if (params == null) params = new Object[0];
                    Class<?>[] paramClasses = 
                        Arrays.stream(params).map(p -> p.getClass())
                            .collect(Collectors.toList())
                            .toArray(new Class<?>[0]);    
                    Method m = cls.getMethod(staticField, paramClasses);
                    return (T)m.invoke(null, params);
                }
            }            
        } catch (Exception e) {
            log.err("[Config.getInstance] Cannot get instance for %s%nError: %s%n", instancePath, e.toString());
            System.exit(1);
        }
        return null;
    }    
    @SuppressWarnings("unchecked")
    public <T> Optional<T> getInstanceOpt(Log log, String instancePathKey, Object... params) {
        String instancePath = get(instancePathKey, "");
        String[] parts = instancePath.split("\\+");
        try {
            if (parts.length == 1) {
                //assuming class name was provided
                if (params == null) params = new Object[0];
                Class<?>[] paramClasses = 
                    Arrays.stream(params).map(p -> p.getClass())
                        .collect(Collectors.toList())
                        .toArray(new Class<?>[0]);
                return Optional.of((T)Class.forName(instancePath).getConstructor(paramClasses).newInstance(params));
            } else {
                //static field was provided
                String staticField = parts[parts.length - 1];
                
                Class<?> cls = Class.forName(String.join("+", Arrays.copyOfRange(parts, 0, parts.length - 1)));
                try 
                {
                    Field f = cls.getDeclaredField(staticField);
                    return Optional.of((T)f.get(null));
                } catch (NoSuchFieldException e)
                {
                    if (params == null) params = new Object[0];
                    Class<?>[] paramClasses = 
                        Arrays.stream(params).map(p -> p.getClass())
                            .collect(Collectors.toList())
                            .toArray(new Class<?>[0]);    
                    Method m = cls.getMethod(staticField, paramClasses);
                    return Optional.of((T)m.invoke(null, params));
                }
            }            
        } catch (Exception e) {
            if (instancePath != null && !instancePath.equals(""))
            {
                log.err("[Config.getInstanceOpt] Cannot get instance for %s%nError: %s%n", instancePath, e.toString());
                System.exit(1);
            }
        }
        return Optional.empty();
    }    
    public static class Network {
        public String policyPath;    
        public String host; 
        public int port;   
        public void validate(Log log, String fileName) {
            if (this.host == "" || this.host == null) {
                log.err("[Config.validate] In given config %s parameter hostname is missing", fileName);
                System.exit(1);
            }     
            if (this.port < 1024 && this.port > 65535) {
                log.err("[Config.validate] In given config %s parameter port is missing or invalid", fileName);
                System.exit(1);            
            }
        }  
        public NetworkPolicy createPolicy(Config config, Log log) {
            return config.getInstance(log, policyPath);
        }
    }
    protected String configFileName;
    protected Properties props = new Properties();

    protected String outputFolder;
    // protected Map<Integer, Network> networkInterfaces = new HashMap<>();
    // protected List<Config> children = new ArrayList<>();
    
    // protected String programFolder;
    // protected String transformsFolder;
    // protected String presetUrl;    
    // protected int evalTries;
    // protected Log log;
    // protected String evolutionAlgorithmName;
    // protected String selectionPolicyName;
    // protected String genotypeIndexFile; //GENOTYPE_INDEX_FILE = "genotypeIndex.bro";
    // protected String studentsFile; //STUDENTS_FILE = "students.bro";
    // protected String studentsStatsFile; //STUDENTS_STATS_FILE = "studentStats.bro";
    // protected String genotypesFile; //GENOTYPES_FILE = "genotypes.bro";
    // protected String genotypeEvolFile; //GENOTYPE_EVOL_FILE = "genotypeEvol.bro";
    // protected Config parentConfig;

    public Config() {
        // this.log = log;
        // this.parentConfig = parentConfig;
    }

    public Map<Integer, Network> createNetworkInterfaces(Log log) {  
        final Map<Integer, Network> networkInterfaces = new HashMap<>();
        ToIntFunction<String> portParser = 
            value -> {
                try {
                    return Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    log.err("[Config.LoadFromFile] Number format error. Check given port in config");
                    System.exit(1);
                    return 0;
                }                    
            };
        props.forEach((k, v) -> {
            String name = k.toString();
            String value = v.toString();
            if (name.startsWith("evoparsons.net")) {
                String[] nameParts = name.split("\\.");
                if (nameParts.length <= 2)
                {
                    networkInterfaces.computeIfAbsent(0, (key) -> new Network())
                        .policyPath = value;
                }
                else {
                    try {
                        Network network = 
                            networkInterfaces.computeIfAbsent(Integer.valueOf(nameParts[2]), (key) -> new Network());
                        if (nameParts.length == 3)
                            network.policyPath = value;
                        else if (nameParts.length == 4) {
                            if (nameParts[3].equals("hostname"))
                                network.host = value;
                            else if (nameParts[3].equals("port"))
                                network.port = portParser.applyAsInt(value);
                            else 
                                log.log("[Config.LoadFromFile] Ignoring unknown property %s=%s", name, value);
                        } else 
                            log.log("[Config.LoadFromFile] Ignoring unknown property %s=%s", name, value);
                    } catch (NumberFormatException e) {
                        Network network = 
                            networkInterfaces.computeIfAbsent(0, (key) -> new Network());                            
                        if (nameParts.length == 3)
                        {
                            if (nameParts[2].equals("hostname"))
                                network.host = value;
                            else if (nameParts[2].equals("port"))
                                network.port = portParser.applyAsInt(value);
                            else 
                                log.log("[Config.LoadFromFile] Ignoring unknown property %s=%s", name, value);
                        } else 
                            log.log("[Config.LoadFromFile] Ignoring unknown property %s=%s", name, value);
                    }
                }
            }
        });
        return networkInterfaces;
    }
    public String getOutputFolder(Log log) {         
        if (outputFolder == null) {
            this.outputFolder = props.getProperty("evoparsons.output");
            if (this.outputFolder.equals("") || this.outputFolder == null) {
                log.err("[Config.getOutputFolder] In given config %s parameter evoparsons.outputFolder is missing", configFileName);
                System.exit(1);
            }                   
            try {
                Paths.get(outputFolder).toFile().mkdir();
            } catch (Exception e) {
                System.err.format("[Config.getOutputFolder] Error verifying output folder %s%n", outputFolder);
                System.exit(1);
            }    
        }
        return outputFolder; 
    }
    // public String getProgramsFolder() { return programFolder; }
    // public String getTransformsFolder() { return transformsFolder; }
    // public String getPresetUrl() {return presetUrl; }
    public String getConfigFileName() { return configFileName; }
    // public String getEvolutionAlgorithmName() { return evolutionAlgorithmName; }
    // public String getSelectionPolicyName() { return selectionPolicyName; }
    // public String getGenotypeIndexFile() { return genotypeIndexFile; }
    // public String getStudentsFile() { return studentsFile; }
    // public String getStudentsStatsFile() { return studentsStatsFile; }
    // public String getGenotypesFile() { return genotypesFile; }
    // public String getGenotypeEvolFile() { return genotypeEvolFile; }
    // public int getEvalTries() { return evalTries; }

    public static Config FromFile(Log log, String url) {
        return new Config().AddFromFile(log, url);
    }

    public String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    // public Optional<Library> getLib(Log log) {
    //     return getInstanceOpt(log, "evoparsons.lib");
    // }

    public List<Config> buildChildConfigs() 
    {
        return null;
    }
    public Config AddFromFile(Log log, String fileName) {
        this.configFileName = fileName;
        try (InputStream fileStream = new FileInputStream(this.configFileName))
        {
            props.load(fileStream);

            // this.evolutionAlgorithmName = props.getProperty("evoparsons.ea");

            // if (this.evolutionAlgorithmName.equals("groups"))
            // {
            //     //recursivelly read other configs;
            //     props.forEach((k, v) -> {
            //         String name = k.toString();
            //         String value = v.toString();                    
            //         if (name.startsWith("evoparsons.ea.")) {
            //             children.add(Config.FromFile(log, value, this).validate());
            //         }
            //     });
            // }
            

        //     this.outputFolder = props.getProperty("evoparsons.outputFolder");
        //     this.programFolder = props.getProperty("evoparsons.programs");
        //     this.presetUrl = props.getProperty("evoparsons.preset");
        //     this.studentsFile = props.getProperty("evoparsons.studentsFile");
        //     this.studentsStatsFile = props.getProperty("evoparsons.studentsStatsFile");
        //     this.genotypeIndexFile = props.getProperty("evoparsons.genotypeIndexFile");
        //     this.genotypesFile = props.getProperty("evoparsons.genotypesFile");
        //     this.genotypeEvolFile = props.getProperty("evoparsons.genotypeEvolFile");            
        //     this.selectionPolicyName = props.getProperty("evoparsons.distributionPolicy");
        //     this.transformsFolder = props.getProperty("evoparsons.transforms");
        //     try {
        //         String evalTries = props.getProperty("evoparsons.evalTries");
        //         this.evalTries = Integer.parseInt(evalTries);
        //     } catch (NumberFormatException e) {
        //         log.log("[Config.LoadFromFile] Using default value for evalTries=2");
        //         this.evalTries = 2;
        //     }
        }
        catch (IOException e) {
            log.err("[Config.LoadFromFile] Error reading config %s: %s", fileName, e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        return this;
    }

    // public Config validate() {
    //     networkInterfaces.forEach((id, network) -> network.validate(log, this.getConfigFileName()));
    //     if (this.outputFolder == "" || this.outputFolder == null) {
    //         log.err("[Config.validate] In given config %s parameter evoparsons.outputFolder is missing", this.getOutputFolder());
    //         System.exit(1);
    //     }             
    //     if (this.programFolder == "" || this.programFolder == null) {
    //         log.err("[Config.validate] In given config %s parameter evoparsons.programs is missing", this.getConfigFileName());
    //         System.exit(1);
    //     }        
    //     if (this.transformsFolder == "" || this.transformsFolder == null) {
    //         log.err("[Config.validate] In given config %s parameter evoparsons.transforms is missing", this.getConfigFileName());
    //         System.exit(1);
    //     }        
    //     if (this.evolutionAlgorithmName == null)        {
    //         log.err("[Config.validate] Evaluation Algorithm was not set in config");
    //         System.exit(1);
    //     }
    //     if (this.evolutionAlgorithmName.equals("preset") && (presetUrl == null))
    //     {
    //         log.err("[Config.validate] Preset File name was not set in config");
    //         System.exit(1);
    //     }        
    //     if (this.selectionPolicyName == null)        {
    //         log.err("[Config.validate] Selection policy was not set in config");
    //         System.exit(1);
    //     }        
    //     return this;
    // }

    public List<String> getList(final String prefix) {
        return props.entrySet().stream().filter(kv -> ((String)kv.getKey()).startsWith(prefix))
                    .map(kv -> (String)kv.getValue())
                    .collect(Collectors.toList());
    }

	public Broker init(Log log, Broker parent) {

		Broker broker = this.getInstance(log, "evoparsons.broker", log, this, parent);
		this.createNetworkInterfaces(log).forEach((id, networkConfig) ->
			networkConfig.createPolicy(this, log).startInterface(log, networkConfig, this, broker.getUIInterface()));

        BrokerEAInterface brokerEAInterface = broker.getEAInterface();
        EAStarter eaStarter = null;
        if (brokerEAInterface != null)
        {
            eaStarter = this.<EAStarter>getInstanceOpt(log, "evoparsons.ea", log, this, brokerEAInterface).orElse(null);
            if (eaStarter != null) {
                if (brokerEAInterface.startFresh()) 
                    eaStarter.runFresh(this.getConfigFileName());
                else 
                    eaStarter.runCheckpoint(this.getConfigFileName());
            }
        }		
		return broker;
	}

}