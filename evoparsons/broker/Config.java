package evoparsons.broker;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.ToIntFunction;

public class Config {
    public static class Network {
        public String brokerName;    
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
    }
    protected Map<Integer, Network> networkInterfaces = new HashMap<>();
    protected String outputFolder;
    protected String programFolder;
    protected String transformsFolder;
    protected String presetUrl;
    protected String configFileName;
    protected int evalTries;
    protected Log log;
    protected String evolutionAlgorithmName;
    protected String selectionPolicyName;

    public Config(Log log) {
        this.log = log;
    }

    public Map<Integer, Network> getNetworkInterfaces() { return networkInterfaces; }
    public String getOutputFolder() { return outputFolder; }
    public String getProgramsFolder() { return programFolder; }
    public String getTransformsFolder() { return transformsFolder; }
    public String getPresetUrl() {return presetUrl; }
    public String getConfigFileName() { return configFileName; }
    public String getEvolutionAlgorithmName() { return evolutionAlgorithmName; }
    public String getSelectionPolicyName() { return selectionPolicyName; }
    public int getEvalTries() { return evalTries; }

    public static Config FromFile(Log log, String url) {
        Config conf = new Config(log);
        return conf.AddFromFile(url);
    }
    public Config AddFromFile(String fileName) {
        this.configFileName = fileName;
        Properties props = new Properties();
        try (InputStream fileStream = new FileInputStream(this.configFileName))
        {
            props.load(fileStream);
            
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
                if (name.startsWith("evoparsons.broker")) {
                    String[] nameParts = name.split("\\.");
                    if (nameParts.length <= 2)
                    {
                        networkInterfaces.computeIfAbsent(0, (key) -> new Network())
                            .brokerName = value;
                    }
                    else {
                        try {
                            Network network = 
                                networkInterfaces.computeIfAbsent(Integer.valueOf(nameParts[2]), (key) -> new Network());
                            if (nameParts.length == 3)
                                network.brokerName = value;
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

            this.outputFolder = props.getProperty("evoparsons.outputFolder");
            this.programFolder = props.getProperty("evoparsons.programs");
            this.presetUrl = props.getProperty("evoparsons.preset");
            this.evolutionAlgorithmName = props.getProperty("evoparsons.ea");
            this.selectionPolicyName = props.getProperty("evoparsons.distributionPolicy");
            this.transformsFolder = props.getProperty("evoparsons.transforms");
            try {
                String evalTries = props.getProperty("evoparsons.evalTries");
                this.evalTries = Integer.parseInt(evalTries);
            } catch (NumberFormatException e) {
                log.log("[Config.LoadFromFile] Using default value for evalTries=2");
                this.evalTries = 2;
            }
        }
        catch (IOException e) {
            log.err("[Config.LoadFromFile] Error reading config %s: %s", fileName, e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        return this;
    }

    public Config validate() {
        networkInterfaces.forEach((id, network) -> network.validate(log, this.getConfigFileName()));
        if (this.outputFolder == "" || this.outputFolder == null) {
            log.err("[Config.validate] In given config %s parameter evoparsons.outputFolder is missing", this.getOutputFolder());
            System.exit(1);
        }             
        if (this.programFolder == "" || this.programFolder == null) {
            log.err("[Config.validate] In given config %s parameter evoparsons.programs is missing", this.getConfigFileName());
            System.exit(1);
        }        
        if (this.transformsFolder == "" || this.transformsFolder == null) {
            log.err("[Config.validate] In given config %s parameter evoparsons.transforms is missing", this.getConfigFileName());
            System.exit(1);
        }        
        if (this.evolutionAlgorithmName == null)        {
            log.err("[Config.validate] Evaluation Algorithm was not set in config");
            System.exit(1);
        }
        if (this.evolutionAlgorithmName.equals("preset") && (presetUrl == null))
        {
            log.err("[Config.validate] Preset File name was not set in config");
            System.exit(1);
        }        
        if (this.selectionPolicyName == null)        {
            log.err("[Config.validate] Selection policy was not set in config");
            System.exit(1);
        }        
        return this;
    }

}