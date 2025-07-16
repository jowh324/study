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
                .password(encodedPassword) // 암호화된 비밀번호 저장
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
