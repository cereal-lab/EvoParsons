package evoparsons.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import org.bson.Document;

import evoparsons.broker.Config;

public class MongoAttemptRepo implements IRepo<String, String> {

    private Config config;
    private String collectionName;
    public MongoAttemptRepo(Config config) {
        this.config = config;   
        this.collectionName = config.get("evoparsons.repo.attempts.name", "attempts-" + config.getConfigFileName());
    }

    @Override
    public String getName() {
        return this.collectionName;
    }


    @Override
    public String get(String id) {
        //id has format - studentId/puzzleId/attemptId
        String[] idParts = id.split("/");
        if (idParts.length != 3) return null;
        try (MongoClient client = MongoClients.create(config.connectionString))
        {
            MongoDatabase db = client.getDatabase("evoDB");
            MongoCollection<Document> collection = db.getCollection(this.collectionName);
            Document result = collection.find(Filters.and(Filters.eq("studentId", idParts[0]), Filters.eq("puzzleId", idParts[1]), Filters.eq("attemptId", idParts[2]))).first();
            return result.toJson();
        }
    }

    @Override
    public void update(List<String> entity) {
        entity.stream()
            .forEach(json -> {
                Document doc = Document.parse(json);
                try (MongoClient client = MongoClients.create(config.connectionString))
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
    public void insert(List<String> entities) {
        List<Document> docs = 
            entities.stream().map(Document::parse).collect(Collectors.toList());
        try (MongoClient client = MongoClients.create(config.connectionString))
        {
            MongoDatabase db = client.getDatabase("evoDB");
            MongoCollection<Document> collection = db.getCollection(this.collectionName);
            collection.insertMany(docs);
        }
    }

    @Override
    public Map<String, String> getAll() {
        try (MongoClient client = MongoClients.create(config.connectionString))
        {
            MongoDatabase db = client.getDatabase("evoDB");
            MongoCollection<Document> collection = db.getCollection(this.collectionName);
            Map<String, String> mp = new HashMap<>();
            collection.find().forEach((Document doc) -> 
            {
                String id = doc.get("studentId") + "/" + doc.get("puzzleId") + "/" + doc.get("attemptId");
                mp.put(id, doc.toJson());
            });
            return mp;
        }
    }      

}