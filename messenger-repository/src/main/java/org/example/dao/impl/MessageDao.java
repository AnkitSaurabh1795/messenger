package org.example.dao.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.client.model.Filters;
import lombok.RequiredArgsConstructor;
import org.bson.conversions.Bson;
import org.example.dao.IMessageDao;
import org.example.entity.MessageEntity;
import org.example.helper.MongodbHelper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MessageDao implements IMessageDao {
    private static final String COLLECTION_NAME = "message";

    private final MongodbHelper mongodbHelper;


    @Override
    public void persist(MessageEntity message, String messageId) {
        try {
            Date date = new Date();
            message.setCreatedAt(date.getTime());
            message.setUpdatedAt(date.getTime());
            mongodbHelper.saveData(COLLECTION_NAME, message, messageId);
        } catch(Exception ex) {
            //TODO:Make Exception handler
            throw ex;
        }
    }

    @Override
    public List<MessageEntity> fetchAllUnreadMessage(Integer pageNumber, Integer pageSize, String userId, boolean isRead) {
        try {
            Bson filter = Filters.and(
                    Filters.eq("toUserId", userId ),
                    Filters.eq("isRead", isRead)
            );
            return mongodbHelper.fetchAllWithFilterAndPaginated(COLLECTION_NAME, filter, pageNumber, pageSize, MessageEntity.class);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public List<MessageEntity> fetchChatHistory(String fromUserId, String toUserId) throws JsonProcessingException {
        try {
            Bson filter = Filters.and(
                    Filters.eq("fromUserId", fromUserId ),
                    Filters.eq("toUserId", toUserId)
            );
            return mongodbHelper.fetchAllWithFilter(COLLECTION_NAME, filter, MessageEntity.class);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
