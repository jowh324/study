package com.example.studygroup.Service;

import com.example.studygroup.Entitiy.Users;
import com.example.studygroup.Repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public CustomUserDetailsService(UserRepository userRepo, PasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.encoder   = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users u = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email + " not found"));
        return User.builder()
                .username(u.getEmail())
                .password(u.getPassword())   // DB에 이미 인코딩된 상태여야 함
                .roles("USER")               // 필요에 따라 권한 설정
                .build();
    }
}
