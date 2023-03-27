package eci.arep;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class MongoServiceImpl{

    private static final MongoServiceImpl INSTANCE = new MongoServiceImpl();


    private MongoClient mongoClient;
    private MongoDatabase database;
    MongoCollection<Document> collection;
    private static MongoServiceImpl instance;

    private MongoServiceImpl() {
        System.out.println();
        this.mongoClient = MongoClients.create("mongodb://172.31.57.61:27017/db");
        this.database = mongoClient.getDatabase("db");
        this.collection = database.getCollection("test");
    }

    public static MongoServiceImpl getInstance(){
        if(instance == null){
            instance = new MongoServiceImpl();
        }
        return instance;
    }

    public void create(String string){
        TimeZone timeZone = TimeZone.getTimeZone("America/Bogota");
        Document document = new Document("Cadena", string).append("Fecha", LocalDateTime.now(timeZone.toZoneId()));
        collection.insertOne(document);
    }

    public String getString(){
        // String response = "";
        JSONObject jsonObject = new JSONObject();
        List<Document> documents = collection.find().sort(new Document("Fecha", -1)).limit(10).into(new ArrayList<>());
        for (Document doc : documents){
            Document newDoc = new Document();
            String id = doc.remove("_id").toString();
            System.out.println(doc);
            // response = doc.get("Cadena").toString() + doc.get("Fecha").toString();
            newDoc.append("Cadena", doc.get("Cadena")).append("Fecha", doc.get("Fecha").toString());
            jsonObject.put(id, newDoc.toJson());
        }
        return jsonObject.toString();
    }
}
