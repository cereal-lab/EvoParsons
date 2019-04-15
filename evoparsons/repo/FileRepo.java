package evoparsons.repo;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;

import evoparsons.broker.Config;
import evoparsons.broker.Log;

public abstract class FileRepo<ID, T> implements IRepo<ID, T> {
    protected File file; 
    protected long lastModified;
    protected Boolean shouldRetry;
    protected Log log;
    protected abstract void setNewState(Reader state) throws IOException;

    protected void watch() {
        if ((lastModified != file.lastModified()) || shouldRetry) {            
            load();
        }
    }

    protected void load() {
        try (FileReader reader = new FileReader(file)) {            
            setNewState(reader);
            lastModified = file.lastModified();
            shouldRetry = false;
        } catch (Exception e) {
            log.err("[FileRepo.load] %s: %s", file.getPath(), e.getMessage());
            shouldRetry = true;
            //e.printStackTrace();
            //System.exit(1);
        }
    }

    public FileRepo(Config config)
    {
        this.log = config.getLog();
    }
}