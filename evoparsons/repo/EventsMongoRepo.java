package evoparsons.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

import org.bson.Document;

import evoparsons.broker.Config;
import evoparsons.broker.Log;

public class EventsMongoRepo implements IRepo<String, Events> {

    private Config config;
    private String collectionName;
    private String connectionString;
    private String dbName;
    public EventsMongoRepo(Config config) {
        Log log = config.getLog();
        this.connectionString = config.getConnectionString();
        this.dbName = config.getDbName();
        if (connectionString == null || connectionString.equals("")) {
            log.err("[EventsMongoRepo] connection string evoparsons.db was not specified in config!!");
            System.exit(1);
        }        
        this.config = config;   
        this.collectionName = config.get("repo.events.name", "evs-" + config.getConfigFileName());
    }

    @Override
    public String getName() {
        return this.collectionName;
    }


    @Override
    public Events get(String id) {
        //id has format - studentId/puzzleId/attemptId
        String[] idParts = id.split("/");
        if (idParts.length != 2) return null;
        String sid = idParts[0];
        int puzzleId = 0;
        try {
            puzzleId = Integer.valueOf(idParts[1]);
        } catch (NumberFormatException e) {
            return null;
        }        
        try (MongoClient client = MongoClients.create(connectionString))
        {
            MongoDatabase db = client.getDatabase(dbName);
            MongoCollection<Document> collection = db.getCollection(this.collectionName);
            Document result = 
                collection.find(
                    Filters.and(
                        Filters.eq("studentId", sid), 
                        Filters.eq("puzzleId", puzzleId))
                    ).first();
            return new Events(sid, puzzleId, result.toJson());
        }
    }

    @Override
    public void update(List<Events> entity) {
        entity.stream()
            .forEach(events -> {                
                try (MongoClient client = MongoClients.create(connectionString))
                {
                    Document doc = Document.parse(events.events);
                    MongoDatabase db = client.getDatabase(dbName);
                    MongoCollection<Document> collection = db.getCollection(this.collectionName);
                    UpdateResult res = 
                        collection.updateOne(
                            Filters.and(
                                Filters.eq("studentId", events.sid), 
                                Filters.eq("puzzleId", events.puzzleId)),
                                Updates.addEachToSet("events", doc.get("events", List.class))
                                );
                    if (res.getModifiedCount() == 0) 
                        collection.insertOne(doc);
                }
            
            });
    }

    @Override
    public void insert(List<Events> entities) {
        List<Document> docs = 
            entities.stream().map(event -> Document.parse(event.events)).collect(Collectors.toList());
        try (MongoClient client = MongoClients.create(connectionString))
        {
            MongoDatabase db = client.getDatabase(dbName);
            MongoCollection<Document> collection = db.getCollection(this.collectionName);
            collection.insertMany(docs);
        }
    }

    @Override
    public Map<String, Events> getAll() {
        try (MongoClient client = MongoClients.create(connectionString))
        {
            MongoDatabase db = client.getDatabase(dbName);
            MongoCollection<Document> collection = db.getCollection(this.collectionName);
            Map<String, Events> mp = new HashMap<>();
            Consumer<Document> c = 
                (Document doc) -> 
                {
                    String id = doc.get("studentId") + "/" + doc.get("puzzleId");
                    mp.put(id, new Events((String)doc.get("studentId"), (int)doc.get("puzzleId"), doc.toJson()));
                };
            collection.find().forEach(c);
            return mp;
        }
    }      

}