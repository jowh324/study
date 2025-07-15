package com.example.studygroup;

import java.time.Instant;

public class ChatMessage {
    public enum MessageType { CHAT, JOIN, LEAVE }

    private MessageType type;
    private String sender;
    private String content;
    private Instant timestamp;

    public ChatMessage() {}

    public MessageType getType() { return type; }
    public void setType(MessageType type) { this.type = type; }
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }
}
