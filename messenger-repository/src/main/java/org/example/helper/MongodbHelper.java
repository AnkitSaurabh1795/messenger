package org.example.helper;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.example.common.Utility;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.*;

import static org.example.common.Utility.readObject;
import static org.example.common.Utility.writeString;
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
            throw new RuntimeException();
        }
    }

    public <T> List<T> fetchAllWithFilterAndPaginated(String collectionName, Bson bson, Integer pageNumber,
                                                      Integer pageSize, Class<T> cls) {
        List<T> result = new ArrayList<>();

        try {
            MongoCollection<Document> collection = database.getCollection(collectionName);

            List<Document> documents = collection.find(bson)
                    .sort(Sorts.ascending("createdAt"))
                    .skip(pageNumber * pageSize)
                    .limit(pageSize)
                    .into(new ArrayList<>());

            for (Document document : documents) {
                T object = readObject(document.toJson(), cls);
                result.add(object);
            }

        } catch (Exception e) {
            throw new RuntimeException();
        }
        return result;
    }

    public <T> List<T> fetchAllWithFilter(String collectionName, Bson bson, Class<T> cls) throws JsonProcessingException {
        List<T> result = new ArrayList<>();

        try {
            MongoCollection<Document> collection = database.getCollection(collectionName);

            List<Document> documents = collection.find(bson)
                    .sort(Sorts.ascending("createdAt"))
                    .into(new ArrayList<>());

            for (Document document : documents) {
                T object = readObject(document.toJson(), cls);
                result.add(object);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
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
                            result.add(readObject(Utility.writeString(document),cls));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (Exception e){
            throw new RuntimeException();
        }
        return result;
    }

    public <T> Optional<T> findOptionalByFilter(String collectionName, Bson filter, Class<T> cls) {
        T result = null;
        try {
            MongoCollection<Document> collection = database.getCollection(collectionName);
            Document searchResult = collection.find(filter).first();
            if(Objects.nonNull(searchResult)) {
                result = readObject(Utility.writeString(searchResult), cls);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(result);
    }

    public boolean updateById(String collectionName, String id, Object valuesToUpdate) {
        return this.updateById(collectionName, id, valuesToUpdate, false);
    }

    private boolean updateById(String collectionName, String id, Object valuesToUpdate, boolean upsert) {
        try {
            MongoCollection<Document> collection = database.getCollection(collectionName);
            Map<String, Object> toUpdate = Utility.convertObjectToMap(valuesToUpdate);
            List<Bson> updates = new ArrayList();
            toUpdate.forEach((key, value) -> {
                if (Objects.nonNull(value)) {
                    if (value instanceof Map && ObjectUtils.isEmpty(value)) {
                        updates.add(Updates.unset(key));
                    } else {
                        updates.add(Updates.set(key, value));
                    }
                }

            });
            Bson update = Updates.combine(updates);
            UpdateResult updateResult = collection.updateOne(Filters.eq("_id", id), update, (new UpdateOptions()).upsert(upsert));
            boolean updateDone = updateResult.getModifiedCount() > 0L;
            return updateDone;
        } catch (Exception ex) {
            log.error("[MongoDBDb]Failed to write in database with error = ", ex);
            throw new RuntimeException(ex);
        }
    }

    public void bulkUpdate(String collectionName, Bson filter, Bson param)  {
        if (!Objects.isNull(filter) && !Objects.isNull(param)) {
            try {
                MongoCollection<Document> collection = database.getCollection(collectionName);
                collection.updateMany(filter, param);
            } catch (Exception var5) {
                log.error("[MongoDb]Failed to write in database with error = ", var5);
                throw new RuntimeException();
            }
        } else {
            log.error("[MongoDb]filter/param cannot be null for bulk update");
            throw new RuntimeException();
        }
    }
}
