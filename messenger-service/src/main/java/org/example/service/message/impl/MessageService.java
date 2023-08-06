package org.example.service.message.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.IMessageDao;
import org.example.entity.MessageEntity;
import org.example.helper.UserHelper;
import org.example.model.request.ChatHistoryRequest;
import org.example.model.request.SendMessageRequest;
import org.example.model.response.*;
import org.example.service.message.IMessageService;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.example.util.Util.generateMessageId;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService implements IMessageService {

    private final UserHelper userHelper;

    private final IMessageDao messageDao;
    @Override
    public UnreadMessageResponse fetchUnreadMessages(String userName) {
        if(userHelper.isUserLoggedIn(userName) && userHelper.isSessionActive(userName)) {
            String userId = userHelper.getUserID(userName);
            //TODo: add pagination logic on scale
            List<MessageEntity> messageList = messageDao.fetchUserMessage(0, 50, userId,false);
            if(messageList.isEmpty()) {
                return UnreadMessageResponse.builder().status("success").message("No new messages").build();
            }
            return chatResponse(messageList);
        }
        return UnreadMessageResponse.builder().status("success").message("User is not logged in").build();
    }

    @Override
    public String sendMessage(SendMessageRequest sendMessageRequest) {
        if(userHelper.isUserLoggedIn(sendMessageRequest.getFrom()) && userHelper.isSessionActive(sendMessageRequest.getFrom())) {
            MessageEntity message = createMessage(sendMessageRequest);
            messageDao.persist(message, message.getMessageId());
            return "success";
        }
        return "User is not logged in";
    }

    @Override
    public BaseResponse fetchChatHistory(ChatHistoryRequest chatHistoryRequest) throws JsonProcessingException {
        if(userHelper.isUserLoggedIn(chatHistoryRequest.getUser()) && userHelper.isSessionActive(chatHistoryRequest.getUser())) {
            List<MessageEntity> messages = messageDao.fetchChatHistory(chatHistoryRequest.getFriend(), chatHistoryRequest.getUser());
            if(messages.isEmpty()) {
                return BaseResponse.builder().message("success").status("No chat history").build();
            }

            return createChatHistoryResponse(messages);
        }
        return BaseResponse.builder().message("success").status("User not logged in").build();
    }

    private BaseResponse createChatHistoryResponse(List<MessageEntity> messages) {
        ChatHistoryResponse response = (ChatHistoryResponse) ChatHistoryResponse.builder().texts(new ArrayList<>()).message("success")
                .status("You have message(s)").build();

        messages.forEach(message -> {
            response.getTexts().add(Text.builder().userName(message.getFromUserId()).build());

        });

        return response;

    }

    private UnreadMessageResponse chatResponse(List<MessageEntity> messageList) {
        UnreadMessageResponse response = UnreadMessageResponse.builder().message("success").message("You have message(s)")
                .chats(new ArrayList<>()).build();
        Map<String, List<String>> chatMap = new LinkedHashMap<>();
        messageList.forEach(message -> {
            List<String> textList = chatMap.getOrDefault(message.getFromUserId(), new ArrayList<>());
            textList.add(message.getText());
            chatMap.put(message.getFromUserId(), textList);

        });
        chatMap.forEach((userId, messages) -> {
            response.getChats().add(Chat.builder().userName(userId).messages(messages).build());
        });

        return response;
    }

    private MessageEntity createMessage(SendMessageRequest sendMessageRequest) {
        String fromUserId = userHelper.getUserID(sendMessageRequest.getFrom());
        String toUserId = userHelper.getUserID(sendMessageRequest.getTo());
        return MessageEntity.builder().messageId(generateMessageId()).fromUserId(fromUserId).toUserId(toUserId)
                .text(sendMessageRequest.getText()).build();

    }
}
