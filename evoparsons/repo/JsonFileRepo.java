package evoparsons.repo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import org.eclipse.jetty.util.ajax.JSON;

import evoparsons.broker.Config;

public class JsonFileRepo<T> extends FileRepo<String, T> {
    private Map<String, T> inMemoryState = Collections.unmodifiableMap(new HashMap<>());

    public JsonFileRepo(Config config, String file) {
        super(config);
        this.file = Paths.get(config.getOutputFolder(), file).toFile();
        this.log = config.getLog();
        load();
        config.getTP().scheduleAtFixedRate(this::watch, 1, 1, TimeUnit.MINUTES);
    }

    @Override
    public T get(String id) {
        return inMemoryState.get(id);
    }

    @Override
    public void update(List<T> entity, Function<T, String> idGetter) {

    }

    @Override
    protected void setNewState(Reader reader) throws IOException {
        Map<String, T> repo = Collections.unmodifiableMap((Map<String, T>) JSON.parse(reader));
        this.inMemoryState = repo; // atomic    
    }

}