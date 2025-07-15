package com.example.studygroup.DTO;

import java.time.LocalDateTime;

public class BoardDto {
    private Long id;
    private String title;
    private String content;
    private String status;
    private String category;
    private String username;
    private LocalDateTime createdAt;

    public BoardDto() {}

    public BoardDto(Long id, String title, String content, String status,
                    String category, String username, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = status;
        this.category = category;
        this.username = username;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getStatus() { return status; }
    public String getCategory() { return category; }
    public String getUsername() { return username; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setContent(String content) { this.content = content; }
    public void setStatus(String status) { this.status = status; }
    public void setCategory(String category) { this.category = category; }
    public void setUsername(String username) { this.username = username; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

