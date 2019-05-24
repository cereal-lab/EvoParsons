package evoparsons.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.function.Consumer;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;

public class MongoUtils {

    public static void populateProps(Properties props, String prefix, Document doc) {
        for (String key: doc.keySet())
        {
            if (key.equals("_id")) continue;
            String curName = key.equals("_") ? prefix : (prefix.isEmpty() ? key : (prefix + "." + key));
            Object v = doc.get(key);
            if (v instanceof Document) 
                populateProps(props, curName, (Document)v);
            else 
                props.put(curName, v); //v.toString());             //if (v instanceof String) - or array
        }
    }

    private static void addProps(String[] key, int curKeyIndex, String value, Document curDoc) {
        //searching for prop and create by path
        if (key.length == (curKeyIndex + 1)) {
            String curName = key[curKeyIndex];
            if (curDoc.containsKey(curName))
            {
                Object v = curDoc.get(curName);
                if (v instanceof Document){
                    ((Document)v).append("_", value);
                }
                else 
                    curDoc.append(curName, value);
            } else 
            {
                curDoc.append(curName, value);
            }
        } else {
            String curName = key[curKeyIndex];
            if (curDoc.containsKey(curName)) {
                Object v = curDoc.get(curName);
                if (v instanceof Document)
                {
                    addProps(key, curKeyIndex+1, value, (Document)v);
                } else {
                    Document newDoc = new Document("_", v);
                    curDoc.append(curName, newDoc);
                    addProps(key, curKeyIndex+1, value, newDoc);    
                }
            } else {
                Document newDoc = new Document();
                curDoc.append(curName, newDoc);
                addProps(key, curKeyIndex+1, value, newDoc);
            }
        }        
    }

    private static void migrateConfigsToMongo(String connectionString) throws IOException {
        try (MongoClient client = MongoClients.create(connectionString))
        {
            MongoDatabase evoDB = client.getDatabase("evoDB");
            MongoCollection<Document> collection = evoDB.getCollection("config");
            File configDir = new File("C:\\Users\\dvitel\\Documents\\University of South Florida\\Lessons\\Independent Study\\cereal\\EvoParsons\\configs\\dev");
            File[] files = configDir.listFiles();
            for (File file: files) {
                if (file.isFile())
                {
                    String fileName = file.getName();
                    //String id = fileName.split("\\.")[0];
                    final Document doc = new Document("config_file_name", fileName);
                    if (fileName.endsWith(".preset"))
                    {
                        List<List<String>> preset = new ArrayList<>();
                        try (Scanner scanner = new Scanner(file))
                        {
                            while (scanner.hasNextLine())
                            {
                                String[] genotype = scanner.nextLine().split(",\\s*");
                                List<String> genotypeList = Arrays.asList(genotype);
                                preset.add(genotypeList);
                            }
                        }
                        doc.append("preset", preset);
                    } else if (fileName.endsWith(".params"))
                    {
                        Properties props = new Properties();
                        try (InputStream fileStream = new FileInputStream(file))
                        {                        
                            props.load(fileStream);
                        }
                        
                        props.forEach((k, v) -> 
                            {
                                String[] hierarchy = k.toString().split("\\.");
                                addProps(hierarchy, 0, v.toString(), doc);
                            });   
                    }     
                    collection.insertOne(doc);         
                }
            }
        }
    }

    private static void setPresetsForDevConfig(String connectionString) {
        try (MongoClient client = MongoClients.create(connectionString))
        {
            MongoDatabase evoDB = client.getDatabase("evoDB");
            MongoCollection<Document> collection = evoDB.getCollection("config");
            Document doc = collection.find(Filters.eq("_id", new ObjectId("5cc701aecf45c84f5ed20da2"))).first();
            Object data = doc.getEmbedded(Arrays.asList("evoparsons", "broker", "child", "2", "evoparsons", "ea", "preset"), ArrayList.class);
            collection.updateOne(Filters.eq("_id", new ObjectId("5cc71b956650bb591d9a4f5f")),
                Updates.set("broker.child.1.ea.preset", data));
        }
    }

    private static void removeConfigDependencies(String connectionString) {
        try (MongoClient client = MongoClients.create(connectionString))
        {
            MongoDatabase evoDB = client.getDatabase("evoDB");
            MongoCollection<Document> collection = evoDB.getCollection("config");
            Map<String, Document> docs = new HashMap<>();
            Consumer<Document> c = 
                ((Document doc) -> docs.put((String)doc.get("config_file_name"), doc));            
            collection.find(Filters.or(
                Filters.eq("config_file_name", "dev"),
                Filters.eq("config_file_name", "ev.1.params"),
                Filters.eq("config_file_name", "ev.2.params")                
            )).forEach(c);
            docs.forEach((name, doc) -> {
                List<org.bson.conversions.Bson> updates = new ArrayList<>();
                Document evo = (Document)doc.get("evoparsons");
                if (evo == null) return;
                //old preset
                /*
                Object preset = evo.get("preset");
                if (preset instanceof String)
                {
                    String[] presetParts = ((String)preset).split("/");
                    String presetDocId = presetParts[presetParts.length - 1];
                    if (docs.containsKey(presetDocId))
                    {
                        Document presetDoc = docs.get(presetDocId);
                        updates.add(Updates.set("evoparsons.preset", presetDoc.get("preset")));
                    }
                }
                //new preset
                Object ea = evo.get("ea");
                if ((ea != null) && (ea instanceof Document))
                {
                    preset = ((Document)ea).get("preset");                
                    if ((preset != null) && preset instanceof String)
                    {
                        String[] presetParts = ((String)preset).split("/");
                        String presetDocId = presetParts[presetParts.length - 1];
                        if (docs.containsKey(presetDocId))
                        {
                            Document presetDoc = docs.get(presetDocId);
                            updates.add(Updates.set("evoparsons.ea.preset", presetDoc.get("preset")));
                        }
                    }
                }
                */
                String childBroker = doc.getEmbedded(Arrays.asList("evoparsons", "broker", "child", "1"), String.class);                
                if (childBroker != null)
                {
                    String[] childBrokerParts = ((String)childBroker).split("/");
                    String childBrokerDocId = childBrokerParts[childBrokerParts.length - 1];
                    if (docs.containsKey(childBrokerDocId))
                    {
                        Document childBrokerDoc = docs.get(childBrokerDocId);
                        updates.add(Updates.set("broker.child.1", childBrokerDoc));
                    }
                }    
                childBroker = doc.getEmbedded(Arrays.asList("evoparsons", "broker", "child", "2"), String.class);                
                if (childBroker != null)
                {
                    String[] childBrokerParts = ((String)childBroker).split("/");
                    String childBrokerDocId = childBrokerParts[childBrokerParts.length - 1];
                    if (docs.containsKey(childBrokerDocId))
                    {
                        Document childBrokerDoc = docs.get(childBrokerDocId);
                        updates.add(Updates.set("broker.child.2", childBrokerDoc));
                    }
                }          
                if (updates.size() > 0)
                    collection.updateOne(Filters.eq("_id", doc.get("_id")), Updates.combine(updates));
            });
            //Document s18cPreset = collection.find(Filters.eq("_id", new ObjectId("5cc6fb918617dc014f47557e"))).first(); //this is s18c.preset
            //Document s18cParams = collection.find(Filters.eq("_id", "5cc6fb908617dc014f47557d")).first(); //s18c params
            //collection.updateOne(Filters.eq("_id", new ObjectId("5cc6fb908617dc014f47557d")), Updates.set("evoparsons.preset", s18cPreset.get("preset")));
        }
    }

    public static void populatePropsFromFile(String connectionString, String fileName, Properties props) {
        try (MongoClient client = MongoClients.create(connectionString))
        {
            MongoDatabase evoDB = client.getDatabase("evoDB");
            MongoCollection<Document> collection = evoDB.getCollection("config");
            Document doc = collection.find(Filters.eq("config_file_name", fileName)).first();
            populateProps(props, "", doc);
        }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // Properties props = new Properties();
        // populatePropsFromFile("sp19_3515.params", props);
        // try (OutputStream out = new FileOutputStream("tests.params"))
        // {
        //     props.store(out, "");
        // }
        //removeConfigDependencies_s18c();
        //migrateConfigsToMongo();
        //removeConfigDependencies();
        setPresetsForDevConfig("");
    }
}