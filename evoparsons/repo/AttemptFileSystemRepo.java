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

public class AttemptFileSystemRepo implements IRepo<String, Attempt> {

    public Path root; 
    public AttemptFileSystemRepo(Config config) 
    {
        this.root = Paths.get(config.getOutputFolder(), "data");
    }

    @Override
    public String getName() {
        return root.toString();
    }

    @Override
    public Attempt get(String id) {
        return null;
    }

    @Override
    public void update(List<Attempt> entities) throws IOException {
        insert(entities);
    }

    @Override
    public void insert(List<Attempt> entities) throws IOException {
        for (Attempt attempt: entities)
        {
            File file = Paths.get(this.root.toString(), attempt.sid, attempt.puzzleId, String.format("%s.json", attempt.attemptId)).toFile().getAbsoluteFile();
            file.getParentFile().mkdirs();
            try (FileWriter writer = new FileWriter(file)) {
                String text = attempt.attempt;
                writer.write(text);
            }         
        }
    }

    @Override
    public Map<String, Attempt> getAll() {
        return Collections.emptyMap();
    }

}