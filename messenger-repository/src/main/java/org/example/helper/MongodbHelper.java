package org.example.helper;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.example.common.Utility;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.example.constants.MongoConstant.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class MongodbHelper {

    MongoClient mongoClient = MongoClients.create(MONGO_URI);
    MongoDatabase database = mongoClient.getDatabase(DB);

    public void saveData(String collectionName, Object value, String key) {
        try{
            MongoCollection<Document> collection = database.getCollection(collectionName);
            Document messageDoc = new Document(Utility.convertObjectToMap(value));
            messageDoc.put("_id", key);
            collection.insertOne(messageDoc);
        }catch (Exception e){
            throw e;
        }
    }

    public <T> List<T> fetchAllWithFilterAndPaginated(String collectionName, Bson bson, Integer pageNumber,
                                                      Integer pageSize, Class<T> cls) {
        List<T> result = new ArrayList<>();

        try {
            MongoCollection<Document> collection = database.getCollection(collectionName);
            collection.find()
                    .filter(bson)
                    .sort(Sorts.descending(CREATED_AT))
                    .skip(pageNumber)
                    .limit(pageSize)
                    .forEach(document -> {
                        try {
                            result.add(Utility.readObject(Utility.writeString(document),cls));
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return result;
    }

    public <T> List<T> fetchAllWithFilter(String collectionName, Bson bson, Class<T> cls) throws JsonProcessingException {
        List<T> result = new ArrayList<>();

        try {
            MongoCollection<Document> collection = database.getCollection(collectionName);
            collection.find(bson).forEach(document -> {
                try {
                    result.add(Utility.readObject(Utility.writeString(document), cls));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> fetchAllWithoutFilterPaginated(String collectionName, Integer pageNumber, Integer pageSize,
                                                      Class<T> cls) {
        List<T> result = new ArrayList<>();

        try {
            MongoCollection<Document> collection = database.getCollection(collectionName);
            collection.find()
                    .sort(Sorts.descending(CREATED_AT))
                    .skip(pageNumber)
                    .limit(pageSize)
                    .forEach(document -> {
                        try {
                            result.add(Utility.readObject(Utility.writeString(document),cls));
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (Exception e){
            throw new RuntimeException();
        }
        return result;
    }
}
