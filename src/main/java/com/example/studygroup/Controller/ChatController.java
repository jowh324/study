package com.example.studygroup.Controller;

import com.example.studygroup.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.time.Instant;

@Controller
public class ChatController {
    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    // Handle messages sent to /app/chat.sendMessage
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        chatMessage.setTimestamp(Instant.now());
        return chatMessage;
    }


    // Handle new user joining, messages sent to /app/chat.addUser
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in WebSocket session attributes
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        chatMessage.setTimestamp(Instant.now());
        return chatMessage;
    }
    @MessageMapping("/chat.sendPMessage")
    public void sedMessage(@Payload ChatMessage chatMessage) {chatMessage.setTimestamp(Instant.now());
    messagingTemplate.convertAndSendToUser(chatMessage.getSender(), "/queue/messages", chatMessage);
    }
}
