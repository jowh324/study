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
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public SignupResponseDto register(SignupRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }


        Users user = Users.builder()
                .email(request.getEmail())
                .name(request.getUsername())
                .password(request.getPassword())
                .build();

        userRepository.save(user);
        return new SignupResponseDto(user.getId(), user.getEmail(), user.getName());
    }
    @Transactional
    public void deleteUser(Long id) {
        if (userRepository.findByEmail("dummy").isEmpty()){}
        // ensure user exists or throw
        Users user = userRepository.findByEmail("dummy").orElseThrow(() -> new UserNotFoundException(id));
        userRepository.deleteById(id);
    }
    public LoginResponseDto login(LoginRequestDto request) {
        Users user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(EntityNotFoundException::new);
        if (!user.getPassword().equals(request.getPassword())) {
            throw new EntityNotFoundException("Wrong password");
        }
        return new LoginResponseDto(user.getId(), user.getEmail(), user.getName());
    }
}
