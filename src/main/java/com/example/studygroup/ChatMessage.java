package com.example.studygroup;

import java.time.Instant;

public class ChatMessage {
    public enum MessageType { CHAT, JOIN, LEAVE }

    private MessageType type;
    private String sender;
    private String receiver;
    private String content;
    private Instant timestamp;

    public ChatMessage() {}

    public String getReceiver() {
        return receiver;
    }
    public void setReceiver(){this.receiver=receiver;}
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }    public MessageType getType() { return type; }
    public void setType(MessageType type) { this.type = type; }
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
