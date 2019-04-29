package evoparsons.repo;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.jetty.util.ajax.JSON;

import evoparsons.broker.Config;

public abstract class WatchedJsonFileRepo<T> extends FileRepo<String, T> {
    protected volatile Map<String, Object> inMemoryState = Collections.unmodifiableMap(Collections.emptyMap());

    public WatchedJsonFileRepo(Config config, String file) {
        super(config);
        this.file = Paths.get(config.getOutputFolder(), file).toFile();
        this.log = config.getLog();
        load();
        config.getTP().scheduleAtFixedRate(this::watch, 30, 30, TimeUnit.SECONDS);
    }

    @Override 
    protected void setNewState(Reader reader) throws IOException {
        Map<String, Object> repo = Collections.unmodifiableMap((Map<String, Object>) JSON.parse(reader));
        this.inMemoryState = repo; // atomic    
    }    

    // @Override
    // public void update(List<T> entities, Function<T, String> idGetter) {
    //     //NOTE:!!!!! This is only in memory update - file is not modified
    //     //This function is not used yet for watched files
    //     Map<String, T> inMem = new HashMap<>(inMemoryState);
    //     entities.forEach(entity -> 
    //         inMem.put(idGetter.apply(entity), entity)
    //     );
    //     inMemoryState = Collections.unmodifiableMap(inMem);
    // }

    public static class InstructorJsonFileRepo extends WatchedJsonFileRepo<Instructor> {
        public InstructorJsonFileRepo(Config config) {
            super(config, config.get("evoparsons.repo.instructor.file", ""));
        }

        @Override
        public String getName() {
            return this.file.getName();
        }        

        @Override 
        public Instructor get(String id) {
            Map<String, Object> instrInfo = (Map<String, Object>)inMemoryState.get(id);
            if (instrInfo == null) return null;
            String iid = (String)instrInfo.get("iid");
            String isig = (String)instrInfo.get("isig");
            if (iid == null || isig == null) return null;
            return new Instructor(iid, isig);
        }

        @Override
        public void update(List<Instructor> entity) {
            //TODO:NOOP
        }

        @Override
        public void insert(List<Instructor> entities) {
            //TODO:NOOP
        }

        @Override
        public Map<String, Instructor> getAll() {
            return 
                inMemoryState.entrySet().stream()
                    .collect(Collectors.toMap(kv -> kv.getKey(), kv -> get(kv.getKey())));
        }        
    }    
}