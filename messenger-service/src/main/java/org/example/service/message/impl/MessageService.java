package org.example.service.message.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.IMessageDao;
import org.example.entity.MessageEntity;
import org.example.helper.UserHelper;
import org.example.model.response.Chat;
import org.example.model.response.UnreadMessageResponse;
import org.example.service.message.IMessageService;
import org.springframework.stereotype.Service;

import java.util.*;

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
            List<MessageEntity> messageList = messageDao.fetchAllUnreadMessage(0, 50, userId,false);
            if(messageList.isEmpty()) {
                return UnreadMessageResponse.builder().status("success").message("No new messages").build();
            }
            return chatResponse(messageList);
        }
        return UnreadMessageResponse.builder().status("success").message("User is not logged in").build();
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
}
