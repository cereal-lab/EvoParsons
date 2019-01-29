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
    public <T> T getInstance(String instancePathKey, Object... params) {
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
    public <T> Optional<T> getInstanceOpt(String instancePathKey, Object... params) {
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
        // public void validate(Log log, String fileName) {
        //     if (this.host == "" || this.host == null) {
        //         log.err("[Config.validate] In given config %s parameter hostname is missing", fileName);
        //         System.exit(1);
        //     }     
        //     if (this.port < 1024 && this.port > 65535) {
        //         log.err("[Config.validate] In given config %s parameter port is missing or invalid", fileName);
        //         System.exit(1);            
        //     }
        // }  
        public NetworkPolicy createPolicy(Config config) {
            return config.getInstance(policyPath);
        }
    }
    protected String configFileName;
    protected Properties props = new Properties();
    protected Log log;
    protected Config parent;

    protected String outputFolder;

    public Config(Config parent) {
        // this.log = log;
        // this.parentConfig = parentConfig;
        this.parent = parent;
    }

    public Log getLog() {
        if (log == null) {
            if (parent != null) log = parent.getLog();
            if (log == null) log = Log.console;
            log = (Log)getInstanceOpt("evoparsons.log").orElse(Log.console);
        }
        return log;
    }

    public Map<Integer, Network> createNetworkInterfaces() {  
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
    public String getOutputFolder() {         
        if (outputFolder == null) {
            this.outputFolder = props.getProperty("evoparsons.output");
            boolean fromParent = false;
            if ((this.outputFolder.equals("") || this.outputFolder == null) && (parent != null))
            {
                outputFolder = parent.getOutputFolder();            
                fromParent = true;
            }
            if (this.outputFolder.equals("") || this.outputFolder == null) {
                log.err("[Config.getOutputFolder] In given config %s parameter evoparsons.outputFolder is missing", configFileName);
                System.exit(1);
            }                   
            try {
                if (!fromParent) Paths.get(outputFolder).toFile().mkdir();
            } catch (Exception e) {
                System.err.format("[Config.getOutputFolder] Error verifying output folder %s%n", outputFolder);
                System.exit(1);
            }    
        }
        return outputFolder; 
    }
    public String getConfigFileName() { return configFileName; }

    public static Config FromFile(Config parent, String url) {
        Log log = parent == null ? Log.console : parent.getLog();
        return new Config(parent).AddFromFile(log, url);
    }

    public String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    public Config AddFromFile(Log log, String fileName) {
        this.configFileName = fileName;
        try (InputStream fileStream = new FileInputStream(this.configFileName))
        {
            props.load(fileStream);
        }
        catch (IOException e) {
            log.err("[Config.LoadFromFile] Error reading config %s: %s", fileName, e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        return this;
    }

    public List<String> getList(final String prefix) {
        return props.entrySet().stream().filter(kv -> ((String)kv.getKey()).startsWith(prefix))
                    .map(kv -> (String)kv.getValue())
                    .collect(Collectors.toList());
    }

	public Broker init(Broker parent) {

		Broker broker = this.getInstance("evoparsons.broker", this, parent);
		this.createNetworkInterfaces().forEach((id, networkConfig) ->
			networkConfig.createPolicy(this).startInterface(networkConfig, this, broker.getUIInterface()));

        BrokerEAInterface brokerEAInterface = broker.getEAInterface();
        EAStarter eaStarter = null;
        if (brokerEAInterface != null)
        {
            eaStarter = this.<EAStarter>getInstanceOpt("evoparsons.ea", this, brokerEAInterface).orElse(null);
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