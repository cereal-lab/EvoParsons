package evoparsons.psifx.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;

import evoparsons.psifx.AppPSI;

public class Config {
    public final String host;
    public final Optional<Integer> port;
    public final Optional<Integer> maxPuzzleCount;
    private final Properties otherProps;
    private static URL getSessionParametersUrl() throws MalformedURLException {
        return new URL(AppPSI.class.getProtectionDomain().getCodeSource().getLocation(), "session.properties");
    }
    public Config(String host, Optional<Integer> port, Optional<Integer> maxPuzzleCount, Properties otherProps) {
        this.host = host;
        this.port = port;
        this.maxPuzzleCount = maxPuzzleCount;
        this.otherProps = otherProps;
    }
    public void setProp(String name, String value) {
        if (otherProps != null) otherProps.setProperty(name, value);
    }
    public String getProp(String name) {
        if (otherProps != null) {
            String v = otherProps.getProperty(name);
            if (v == null) return "";
            else return v;
        }
        return "";
    }

    public static Config load() {
        try (InputStream is = AppPSI.class.getResourceAsStream("config.properties")) {            
            File file = new File(getSessionParametersUrl().toURI());  
            file.createNewFile();      
            try (InputStream sessionStream = new FileInputStream(file)) 
            {
                Properties defaultProps = new Properties();
                defaultProps.load(is);
                Properties prop = new Properties(defaultProps);
                prop.load(sessionStream);
                Optional<Integer> portOpt;
                try {
                    portOpt = Optional.of(Integer.parseInt(prop.getProperty("evoparsons.port")));
                } catch (Exception e) 
                {
                    portOpt = Optional.empty();
                }
                Optional<Integer> numPuzzlesOpt;
                try {
                    numPuzzlesOpt = Optional.of(Integer.parseInt(prop.getProperty("maxPuzzleNumber")));
                } catch (Exception e) 
                {
                    numPuzzlesOpt = Optional.empty();
                }
                return new Config(prop.getProperty("evoparsons.host"), portOpt, numPuzzlesOpt, prop);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new Config(null, Optional.empty(), Optional.empty(), null);
        }        
    }
    public void save() {
        try {
            File file = new File(getSessionParametersUrl().toURI());  
            file.createNewFile();      
            try(OutputStream output = new FileOutputStream(file))
            {
                otherProps.store(output, null);   
            } 
        } catch(Exception ex) {
            ex.printStackTrace();
        } 
    }


}