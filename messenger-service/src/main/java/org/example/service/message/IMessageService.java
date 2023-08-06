package org.example.service.message;

import org.example.model.response.UnreadMessageResponse;

public interface IMessageService {

    UnreadMessageResponse fetchUnreadMessages(String userName);
}
