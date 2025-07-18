package com.example.studygroup.Controller;

import com.example.studygroup.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.Instant;

@Controller
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;

    public ChatController(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * '/app/private.chat'으로 메시지를 받으면, 수신자의 개인 큐로 직접 메시지를 보냅니다.
     */
    @MessageMapping("/private.chat")
    public void sendPrivateMessage(@Payload ChatMessage chatMessage, Principal principal) {
        String senderEmail=principal.getName();
        chatMessage.setSender(senderEmail);
        chatMessage.setTimestamp(Instant.now());
        // 수신자의 이메일을 기반으로, '/queue/messages/{이메일}' 형태의
        // 명확한 목적지로 메시지를 직접 보냅니다.
        messagingTemplate.convertAndSendToUser(chatMessage.getReceiver(),"/queue/private-messages",chatMessage);
    }
}