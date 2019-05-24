package evoparsons.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import com.mongodb.client.model.UpdateOptions;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import evoparsons.broker.Config;
import evoparsons.broker.Log;
import evoparsons.broker.Student;

public class MongoPojoRepo<T> implements IRepo<String, T> {

    private Config config;
    private String collectionName;
    private CodecRegistry pojoCodecRegistry;
    private Class<T> tClass;
    private String idKey;
    private Function<T, String> getId;
    private String connectionString;
    public MongoPojoRepo(Config config, Class<T> tClass, String configKey, String idKey, Function<T, String> getId) {
        Log log = config.getLog();
        this.connectionString = config.getConnectionString();
        if (connectionString == null || connectionString.equals("")) {
            log.err("[EventsMongoRepo] connection string evoparsons.db was not specified in config!!");
            System.exit(1);
        }                
        this.config = config;   
        this.tClass = tClass;
        this.idKey = idKey;
        this.getId = getId;
        this.collectionName = 
            config.get(configKey, configKey + "-" + config.getConfigFileName());
            //config.get("evoparsons.repo.students.name", "students-" + config.getConfigFileName());
        this.pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
            CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));       
    }

    @Override
    public T get(String id) {
        //id has format - studentId/puzzleId/attemptId
        try (MongoClient client = MongoClients.create(connectionString))
        {
            MongoDatabase db = client.getDatabase("evoDB").withCodecRegistry(this.pojoCodecRegistry);
            MongoCollection<T> collection = db.getCollection(this.collectionName, tClass);
            T result = 
                collection.find(Filters.eq(idKey, id)).first();
                //collection.find(Filters.eq("auth.sid", id)).first();
            return result;
        }
    }

    @Override
    public void update(List<T> entities) {
        entities.stream()
            .forEach(entity -> {
                try (MongoClient client = MongoClients.create(connectionString))
                {
                    MongoDatabase db = client.getDatabase("evoDB").withCodecRegistry(this.pojoCodecRegistry);
                    MongoCollection<T> collection = db.getCollection(this.collectionName, tClass);
                    ReplaceOptions options = new ReplaceOptions().upsert(true);
                    collection.replaceOne(Filters.eq(idKey, getId.apply(entity)), entity, options);
                }            
            });
    }

    @Override
    public String getName() {
        return this.collectionName;
    }    

    @Override
    public void insert(List<T> entities) {
        try (MongoClient client = MongoClients.create(connectionString))
        {
            MongoDatabase db = client.getDatabase("evoDB").withCodecRegistry(this.pojoCodecRegistry);
            MongoCollection<T> collection = db.getCollection(this.collectionName, tClass);
            collection.insertMany(entities);
        }
    }

    @Override
    public Map<String, T> getAll() {
        try (MongoClient client = MongoClients.create(connectionString))
        {
            MongoDatabase db = client.getDatabase("evoDB").withCodecRegistry(this.pojoCodecRegistry);
            MongoCollection<T> collection = db.getCollection(this.collectionName, tClass);
            Map<String, T> mp = new HashMap<>();
            Consumer<T> c = 
                ((T entity) -> 
                    {
                        mp.put(getId.apply(entity), entity);
                    });
            collection.find().forEach(c);
            return mp;
        }
    }      

    public static class Students extends MongoPojoRepo<Student> {
        public Students(Config config) {
            super(config, Student.class, "repo.students.name", "auth.sid", student -> student.getAuth().getSid());
        }
    }

}