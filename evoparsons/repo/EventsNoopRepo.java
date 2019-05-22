package evoparsons.repo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import evoparsons.broker.Config;
import evoparsons.broker.Log;

public class EventsNoopRepo implements IRepo<String, Events> {

    public Config config;
    public Log log;
    public EventsNoopRepo(Config config) 
    {
        this.config = config;
        this.log = config.getLog();
    }

    @Override
    public String getName() {
        return "EventsNoopRepo";
    }

    @Override
    public Events get(String id) {
        log.log("[EventsNoopRepo] Request for events: %s", id);
        return null;
    }

    @Override
    public void update(List<Events> entities) throws IOException {
        entities.forEach(entry ->         
            log.log("[EventsNoopRepo] New events: %s", entry.events));
    }

    @Override
    public void insert(List<Events> entities) throws IOException {
        entities.forEach(entry ->         
            log.log("[EventsNoopRepo] New events to insert: %s", entry.events));
    }

    @Override
    public Map<String, Events> getAll() {
        log.log("[EventsNoopRepo] Request for all events");
        return Collections.emptyMap();
    }

}