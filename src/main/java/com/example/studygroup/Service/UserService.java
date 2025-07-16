package com.example.studygroup.Service;

import com.example.studygroup.DTO.LoginRequestDto;
import com.example.studygroup.DTO.LoginResponseDto;
import com.example.studygroup.DTO.SignupRequestDto;
import com.example.studygroup.DTO.SignupResponseDto;
import com.example.studygroup.Entitiy.Users;
import com.example.studygroup.Repository.UserRepository;
import com.example.studygroup.exception.UserNotFoundException;
import com.example.studygroup.exception.exceptionhandler;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public SignupResponseDto register(SignupRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Users user = Users.builder()
                .email(request.getEmail())
                .name(request.getUsername())
                .password(encodedPassword)
                .build();

        userRepository.save(user);
        return new SignupResponseDto(user.getId(), user.getEmail(), user.getName());
    }


}
