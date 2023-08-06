package org.example.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.request.ChatHistoryRequest;
import org.example.model.request.SendMessageRequest;
import org.example.model.request.UserCreateRequest;
import org.example.model.response.UnreadMessageResponse;
import org.example.service.message.IMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")
@Slf4j
public class MessageController {

    private final IMessageService messageService;

    @GetMapping("/get/unread")
    public ResponseEntity<UnreadMessageResponse> getUnreadMessage(@RequestBody String userName) {
        log.info("Request to get unread message for username {}",userName);
        return ResponseEntity.ok(messageService.fetchUnreadMessages(userName)) ;
    }

    @PostMapping("/send/text/user")
    public ResponseEntity<?> sendMessage(@RequestBody SendMessageRequest request) {
        log.info("Request to send message from username {} to userName",request.getFrom(),request.getTo());
        return ResponseEntity.ok(messageService.sendMessage(request));
    }

    @GetMapping("/get/history")
    public ResponseEntity<?> chatHistory(@RequestBody ChatHistoryRequest request) throws JsonProcessingException {
        log.info("Request to get chat history between username {} to userName",request.getUser(),request.getFriend());
        return ResponseEntity.ok(messageService.fetchChatHistory(request));
    }

}
