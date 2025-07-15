package com.example.studygroup.DTO;

import com.example.studygroup.Entitiy.Category;
import com.example.studygroup.Entitiy.Users;

import java.time.LocalDateTime;

public class BoardRequestDto {
    private String title;
    private String content;
    private String status;
    private String category;

    public BoardRequestDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}