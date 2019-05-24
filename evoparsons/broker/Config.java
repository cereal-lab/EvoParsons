package evoparsons.broker;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

import org.bson.Document;

import evoparsons.repo.IRepo;
import evoparsons.utils.MongoUtils;

public class Config {

    //Reflection - creating instance
    // 1. static field
    // 2. constructor
    // 3. factory method
    @SuppressWarnings("unchecked")
    public <T> T getInstance(String instancePathKey, final Object... params) {
        String instancePath = get(instancePathKey, "");
        String[] parts = instancePath.split("\\+");
        try {
            if (parts.length == 1) {
                //assuming class name was provided
                Constructor<?> c = 
                    Arrays.stream(Class.forName(instancePath).getConstructors())
                        .filter(ctr -> 
                            {
                                Class<?>[] ps = ctr.getParameterTypes();
                                if (ps.length == params.length){
                                    for (int i=0; i < ps.length; i++)
                                    {
                                        Object p = params[i];
                                        Class<?> pc = p.getClass().equals(Class.class) ? (Class<?>)p : p.getClass();
                                        if (!ps[i].isAssignableFrom(pc))
                                            return false;
                                    }
                                    return true;
                                }
                                return false;
                            }
                        ).findFirst().orElse(null);
                if (c == null) {
                    log.err("[Config.getInstance] Cannot get instance for %s%nConstructor was not found for given params", instancePath);
                    System.exit(1);
                }
                Object[] realParams =                         
                    Arrays.stream(params).map(p -> 
                        p.getClass().equals(Class.class) ? null : p)
                        .collect(Collectors.toList())
                        .toArray(new Object[0]);                
                T res = (T)c.newInstance(realParams);
                log.log("%s created", instancePath);
                return res;
            } else {
                //static field was provided
                String staticField = parts[parts.length - 1];
                
                Class<?> cls = Class.forName(String.join("+", Arrays.copyOfRange(parts, 0, parts.length - 1)));
                try 
                {
                    Field f = cls.getDeclaredField(staticField);
                    T res = (T)f.get(null);
                    log.log("%s created", instancePath);
                    return res;
                } catch (NoSuchFieldException e)
                {
                    Method m = 
                        Arrays.stream(cls.getMethods())
                            .filter(mtd -> 
                                {
                                    Class<?>[] ps = mtd.getParameterTypes();
                                    if (ps.length == params.length){
                                        for (int i=0; i < ps.length; i++)
                                        {
                                            Object p = params[i];
                                            Class<?> pc = p.getClass().equals(Class.class) ? (Class<?>)p : p.getClass();
                                            if (!ps[i].isAssignableFrom(pc))
                                                return false;
                                        }
                                        return true;
                                    }
                                    return false;    
                                }).findFirst().orElse(null);
                    if (m == null) {
                        log.err("[Config.getInstance] Cannot get instance for %s%Static method was not found for given params", instancePath);
                        System.exit(1);
                    }          
                    Object[] realParams =                         
                        Arrays.stream(params).map(p -> 
                            p.getClass().equals(Class.class) ? null : p)
                            .collect(Collectors.toList())
                            .toArray(new Object[0]);
                    T res = (T)m.invoke(null, realParams);
                    log.log("%s created", instancePath);
                    return res;
                }
            }            
        } catch (Exception e) {
            log.err("[Config.getInstance] Cannot get instance for %s%nError: %s%n", instancePath, e.toString());
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }    
    public <ID, T, Repo extends IRepo<ID, T>> IRepo<ID, T> getRepoOrDefault(String repoClass, Class<Repo> defaultRepo) {
        Optional<IRepo<ID, T>> repoOpt = this.getInstanceOpt(repoClass, this);
        return repoOpt.orElseGet(() -> {
            if (defaultRepo == null) return null;
            try {
                return defaultRepo.getConstructor(Config.class).newInstance(this);
            } catch (Exception e) {
                log.err("Fallback to default repo failed. Asked repo class: %s. Fallback class: %s", repoClass, defaultRepo.getName());
                log.err("Default fallback Repo does not have constructor which accepts Config instance?");
                log.err(e.getMessage());
                System.exit(1);
                return null;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> getInstanceOpt(String instancePathKey, final Object... params) {
        String instancePath = get(instancePathKey, "");
        String[] parts = instancePath.split("\\+");
        try {
            if (parts.length == 1) {
                //assuming class name was provided
                Constructor<?> c = 
                    Arrays.stream(Class.forName(instancePath).getConstructors())
                        .filter(ctr -> 
                            {
                                Class<?>[] ps = ctr.getParameterTypes();
                                if (ps.length == params.length){
                                    for (int i=0; i < ps.length; i++)
                                    {
                                        Object p = params[i];
                                        Class<?> pc = p.getClass().equals(Class.class) ? (Class<?>)p : p.getClass();
                                        if (!ps[i].isAssignableFrom(pc))
                                            return false;
                                    }
                                    return true;
                                }
                                return false;
                            }
                        ).findFirst().orElse(null);
                Object[] realParams =                         
                    Arrays.stream(params).map(p -> 
                        p.getClass().equals(Class.class) ? null : p)
                        .collect(Collectors.toList())
                        .toArray(new Object[0]);                
                Optional<T> res = Optional.of((T)c.newInstance(realParams));
                if (res.isPresent()) log.log("%s created", instancePath);
                return res;
            } else {
                //static field was provided
                String staticField = parts[parts.length - 1];
                
                Class<?> cls = Class.forName(String.join("+", Arrays.copyOfRange(parts, 0, parts.length - 1)));
                try 
                {
                    Field f = cls.getDeclaredField(staticField);
                    Optional<T> res = Optional.of((T)f.get(null));
                    if (res.isPresent()) log.log("%s created", instancePath);
                    return res;    
                } catch (NoSuchFieldException e)
                {
                    Method m = 
                        Arrays.stream(cls.getMethods())
                            .filter(mtd -> 
                                {
                                    Class<?>[] ps = mtd.getParameterTypes();
                                    if (ps.length == params.length){
                                        for (int i=0; i < ps.length; i++)
                                        {
                                            Object p = params[i];
                                            Class<?> pc = p.getClass().equals(Class.class) ? (Class<?>)p : p.getClass();
                                            if (!ps[i].isAssignableFrom(pc))
                                                return false;
                                        }
                                        return true;
                                    }
                                    return false;    
                                }).findFirst().orElse(null);
                    Object[] realParams =                         
                        Arrays.stream(params).map(p -> 
                            p.getClass().equals(Class.class) ? null : p)
                            .collect(Collectors.toList())
                            .toArray(new Object[0]);
                    Optional<T> res = Optional.of((T)m.invoke(null, realParams));
                    if (res.isPresent()) log.log("%s created", instancePath);
                    return res;    
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
        public String policyName;
        public String policyPath;    
        public String host; 
        public int port;   
        public List<String> servlets = new ArrayList<>();
        public String www;
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
    protected ScheduledThreadPoolExecutor threadPool; 

    protected String outputFolder;
    private String connectionString;

    public Config(Config parent) {
        // this.log = log;
        // this.parentConfig = parentConfig;
        this.parent = parent;
        this.threadPool = new ScheduledThreadPoolExecutor(20);
        //this.connectionString = (connectionString == null) ? ((parent == null) ? null : parent.connectionString) : connectionString;
    }

    public ScheduledThreadPoolExecutor getTP() {
        return threadPool;
    }

    public Log getLog() {
        if (log == null) {
            if (parent != null) 
            {
                log = parent.getLog();
                if (log != null)
                    while (log.parentLog() != null) 
                        log = log.parentLog();
            }
            if (log == null) log = Log.console;
            String header = get("log.header", "");
            log = Log.createWrapper((Log)getInstanceOpt("log").orElse(Log.console), header);
        }
        return log;
    }

    public Map<String, Network> createNetworkInterfaces() {  
        final Map<String, Network> networkInterfaces = new HashMap<>();
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
            if (name.startsWith("net.")) {
                try {
                    int splitPoint = name.indexOf(".", name.indexOf(".") + 1);                 
                    String prefix = splitPoint == -1 ? name : name.substring(0, splitPoint);
                    Network network = 
                        networkInterfaces.computeIfAbsent(prefix, (key) -> new Network());
                    if (name.matches("^net\\.[^.]*$"))
                    {
                        network.policyPath = name;
                        network.policyName = prefix;
                    }
                    else 
                        if (name.matches("^net\\..*?\\.hostname$"))
                            network.host = value;
                        else if (name.matches("^net\\..*?\\.port$"))
                            network.port = portParser.applyAsInt(value);
                        else if (name.matches("^net\\..*?\\.servlet\\.[^.]*$"))
                            network.servlets.add(name);
                        else if (name.matches("^net\\..*?\\.www$"))
                            network.www = value;
                        else if (!name.matches("^net\\..*?\\.servlet\\..*?\\.[^.]*$"))
                            log.log("[Config.LoadFromFile] Ignoring unknown property %s=%s", name, value);
                } catch (Exception e) {
                    log.err("[Config.createNetworkInterfaces] Cannot process %s with value %s", name, value);
                    System.exit(1);
                }
            }
        });
        return networkInterfaces;
    }
    public String getConnectionString() {
        if (connectionString == null) {
            this.connectionString = props.getProperty("db");
            if ((this.connectionString == null || this.connectionString.equals("")) && (parent != null))
            {
                connectionString = parent.getConnectionString();            
            }            
        }
        return connectionString;
    }    
    public String getOutputFolder() {         
        if (outputFolder == null) {
            this.outputFolder = props.getProperty("output");
            boolean fromParent = false;
            if ((this.outputFolder == null || this.outputFolder.equals("")) && (parent != null))
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
        Config config = new Config(parent).AddFromFile(log, url, true);
        config.getLog(); //validation
        config.getOutputFolder(); 
        config.getConnectionString();
        return config;
    }

    public static Config FromProps(Config parent, String name, Properties props) {
        Log log = parent == null ? Log.console : parent.getLog();
        String realName = parent == null ? name : parent.configFileName + ":" + name;
        Config config = new Config(parent).AddFromProps(log, realName, props);
        config.getLog(); //validation
        config.getOutputFolder(); 
        return config;
    }        

    public String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    public Object getObject(String key) {
        return props.get(key);
    }    

    public Config AddFromDB(Log log, String fileName) {
        this.configFileName = fileName;
        try
        {
            MongoUtils.populatePropsFromFile(connectionString, fileName, props);
        }
        catch (Exception e) {            
            log.err("[Config.LoadFromDB] Error reading config %s: %s", fileName, e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        return this;
    }

    public Config AddFromProps(Log log, String name, Properties props) {        
        this.configFileName = name;
        this.props = props;
        return this;
    }    


    private void populateProps(Properties props, String prefix, Document doc) {
        for (String key: doc.keySet())
        {
            if (key.equals("_id")) continue;
            String curName = key.equals("_") ? prefix : (prefix.isEmpty() ? key : (prefix + "." + key));
            Object v = doc.get(key);
            if (v instanceof Document) 
                populateProps(props, curName, (Document)v);
            else 
                props.put(curName, v); //v.toString());             //if (v instanceof String) - or array
        }
    }    

    public Config AddFromFile(Log log, String fileName, boolean tryUrl) {
        this.configFileName = fileName;
        try (InputStream fileStream = new FileInputStream(this.configFileName))
        {
            if (this.configFileName.endsWith(".json"))
            {
                List<String> lines = new ArrayList<>(); 
                try (Scanner scanner = new Scanner(fileStream))
                {
                    while (scanner.hasNextLine())
                    {
                        lines.add(scanner.nextLine());
                    }                    
                }
                String json = String.join(System.lineSeparator(), lines);
                Document doc = Document.parse(json);
                populateProps(props, "", doc);
            } else 
                props.load(fileStream);
        }
        catch (IOException e) {
            if (tryUrl)
            {
                try {
                URL url = new URL(configFileName);
                try(InputStream stream = url.openStream()) {
                    String urlPath = url.getPath();
                    String urlFileName= urlPath.substring(urlPath.lastIndexOf('/') + 1);
                    Path filePath = Paths.get(parent == null ? "." : parent.getOutputFolder(), urlFileName); 
                    Files.copy(stream, filePath, StandardCopyOption.REPLACE_EXISTING);
                    AddFromFile(log, filePath.toString(), false);
                }
                } catch (Exception e2) {
                    log.err("[Config.LoadFromUrl] Error reading config %s: %s", fileName, e2.getMessage());
                    e.printStackTrace();
                    System.exit(1);
                }
            }
            else {
                log.err("[Config.LoadFromFile] Error reading config %s: %s", fileName, e.getMessage());
                e.printStackTrace();
                System.exit(1);
            }
        }
        return this;
    }

    public List<String> getList(final String prefix) {
        return props.entrySet().stream().filter(kv -> ((String)kv.getKey()).startsWith(prefix))
                    .map(kv -> kv.getValue().toString())
                    .collect(Collectors.toList());
    }

    public List<String> getKeyList(final String prefix) {
        return props.entrySet().stream().filter(kv -> ((String)kv.getKey()).startsWith(prefix))
                    .map(kv -> kv.getKey().toString())
                    .collect(Collectors.toList());
    }    

    public Config getSubconfig(final String prefix) {
        Properties childProps = new Properties();
        props.entrySet().stream().filter(kv -> ((String)kv.getKey()).startsWith(prefix))
            .forEach(kv -> {
                String newKey = ((String)kv.getKey()).substring(prefix.length());
                childProps.put(newKey, kv.getValue());
            });
        return Config.FromProps(this, prefix, childProps);
    }

	public Broker init(Broker parent) {

		Broker broker = this.getInstance("broker", this, parent == null ? Broker.class : parent);
		this.createNetworkInterfaces().forEach((id, networkConfig) ->
			networkConfig.createPolicy(this).startInterface(networkConfig, this, broker.getUIInterface()));

        BrokerEAInterface brokerEAInterface = broker.getEAInterface();
        EAStarter eaStarter = null;
        if (brokerEAInterface != null)
        {
            eaStarter = this.<EAStarter>getInstanceOpt("ea", this, brokerEAInterface).orElse(null);
            if (eaStarter != null) {
                if (brokerEAInterface.startFresh()) 
                    eaStarter.runFresh();
                else 
                    eaStarter.runCheckpoint();
            }
        }		
		return broker;
	}

}