package com.example.studygroup.DTO;

public class LoginResponseDto {
    private final Long id;
    private final String email;
    private final String username;

    public LoginResponseDto(Long id, String email, String username) {
        this.id = id;
        this.email = email;
        this.username = username;
    }
    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
}