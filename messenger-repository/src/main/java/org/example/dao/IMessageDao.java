package org.example.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.entity.MessageEntity;

import java.util.List;

public interface IMessageDao {

    void persist(MessageEntity message, String messageId);

    List<MessageEntity> fetchAllUnreadMessage(Integer pageNumber, Integer pageSize, String userId, boolean isRead);

    List<MessageEntity> fetchChatHistory(String fromUserId, String toUserId) throws JsonProcessingException;
}
