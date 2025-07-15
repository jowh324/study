package com.example.studygroup.DTO;

public class SignupResponseDto {

    private Long id;
    private String email;
    private String name;
    public  SignupResponseDto(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getUsername() { return name; }
}
