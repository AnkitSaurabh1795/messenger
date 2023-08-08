package org.example.service.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.model.request.ChatHistoryRequest;
import org.example.model.request.SendMessageRequest;
import org.example.model.response.BaseResponse;
import org.example.model.response.ChatHistoryResponse;
import org.example.model.response.UnreadMessageResponse;

public interface IMessageService {

    BaseResponse fetchUnreadMessages(String userName);

    String sendMessage(SendMessageRequest sendMessageRequest);

    BaseResponse fetchChatHistory(ChatHistoryRequest chatHistoryRequest) throws JsonProcessingException;
}
