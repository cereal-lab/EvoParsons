package evoparsons.repo;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import evoparsons.broker.Config;
import evoparsons.broker.Log;
import evoparsons.broker.Student;
import evoparsons.broker.Utils;

public abstract class HashMapFileRepo<ID, T> implements IRepo<ID, T> {

    protected Map<ID, T> data; 
    protected String file;
    protected Log log;
    public HashMapFileRepo(Config config, String file) {
        //studentsFile = config.get("evoparsons.studentsFile", DEFAULT_STUDENTS_FILE);
        this.log = config.getLog();
        this.file = file;
        //Paths.get(outputFolder, studentsFile).toString()
		data = Utils.<HashMap<ID, T>>loadFromFile(log, file, HashMap<ID, T>::new);   
    }
    @Override
    public T get(ID id) {
        return data.get(id);
    }

    @Override
    public String getName() {
        return file;
    }

    @Override
    public Map<ID, T> getAll() {
        return data;
    }

    protected void save() {
        Utils.saveToFile(log, data, file);
    }

    public static class StudentsHashMapFileRepo extends HashMapFileRepo<String, Student> {
        public StudentsHashMapFileRepo(Config config) {
            super(config, Paths.get(config.getOutputFolder(), config.get("evoparsons.studentsFile", "students.bro")).toString());            
        }

        @Override
        public void update(List<Student> entities) {
            entities.stream().forEach(s -> data.put(s.getAuth().getSid(), s));
            save();
        }

        @Override
        public void insert(List<Student> entities) {
            update(entities);
        }
    }
}