package evoparsons.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import org.bson.Document;

import evoparsons.broker.Config;
import evoparsons.broker.Log;

public class MongoAttemptRepo implements IRepo<String, Attempt> {

    private Config config;
    private String collectionName;
    private String connectionString;
    public MongoAttemptRepo(Config config) {
        Log log = config.getLog();
        this.connectionString = config.getConnectionString();
        if (connectionString == null || connectionString.equals("")) {
            log.err("[MongoAttemptRepo] connection string evoparsons.db was not specified in config!!");
            System.exit(1);
        }        
        this.config = config;   
        this.collectionName = config.get("evoparsons.repo.attempts.name", "attempts-" + config.getConfigFileName());
    }

    @Override
    public String getName() {
        return this.collectionName;
    }


    @Override
    public Attempt get(String id) {
        //id has format - studentId/puzzleId/attemptId
        String[] idParts = id.split("/");
        if (idParts.length != 3) return null;
        try (MongoClient client = MongoClients.create(connectionString))
        {
            MongoDatabase db = client.getDatabase("evoDB");
            MongoCollection<Document> collection = db.getCollection(this.collectionName);
            Document result = collection.find(Filters.and(Filters.eq("studentId", idParts[0]), Filters.eq("puzzleId", idParts[1]), Filters.eq("attemptId", idParts[2]))).first();
            return new Attempt(idParts[0], idParts[1], idParts[2], result.toJson());
        }
    }

    @Override
    public void update(List<Attempt> entity) {
        entity.stream()
            .forEach(attempt -> {
                Document doc = Document.parse(attempt.attempt);
                try (MongoClient client = MongoClients.create(connectionString))
                {
                    MongoDatabase db = client.getDatabase("evoDB");
                    MongoCollection<Document> collection = db.getCollection(this.collectionName);
                    collection.updateOne(
                        Filters.and(
                            Filters.eq("studentId", doc.get("studentId")),
                            Filters.eq("puzzleId", doc.get("puzzleId")),
                            Filters.eq("attemptId", doc.get("attemptId"))), doc);
                }
            
            });
    }

    @Override
    public void insert(List<Attempt> entities) {
        List<Document> docs = 
            entities.stream().map(attempt -> Document.parse(attempt.attempt)).collect(Collectors.toList());
        try (MongoClient client = MongoClients.create(connectionString))
        {
            MongoDatabase db = client.getDatabase("evoDB");
            MongoCollection<Document> collection = db.getCollection(this.collectionName);
            collection.insertMany(docs);
        }
    }

    @Override
    public Map<String, Attempt> getAll() {
        try (MongoClient client = MongoClients.create(connectionString))
        {
            MongoDatabase db = client.getDatabase("evoDB");
            MongoCollection<Document> collection = db.getCollection(this.collectionName);
            Map<String, Attempt> mp = new HashMap<>();
            collection.find().forEach((Document doc) -> 
            {
                String id = doc.get("studentId") + "/" + doc.get("puzzleId") + "/" + doc.get("attemptId");
                mp.put(id, new Attempt((String)doc.get("studentId"), (String)doc.get("puzzleId"), (String)doc.get("attemptId"), doc.toJson()));
            });
            return mp;
        }
    }      

}