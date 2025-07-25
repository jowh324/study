package com.example.studygroup.DTO;

import jakarta.validation.constraints.Email;

public class SignupRequestDto {
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;
    private String username;
    private String password;

    public void SignupRequestDto() {}


    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password;}
}
