package me.neondeex.neolib.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoManager {

    private final MongoClient client;
    private final MongoDatabase database;

    private MongoManager(MongoClient client, String dbName) {
        this.client = client;
        this.database = client.getDatabase(dbName);
    }

    public static MongoManager connect(String uri, String database) {
        return new MongoManager(MongoClients.create(uri), database);
    }

    public MongoCollection<Document> collection(String name) {
        return database.getCollection(name);
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public boolean isRunning() {
        return client != null;
    }

    public void close() {
        if (client != null) client.close();
    }
}
