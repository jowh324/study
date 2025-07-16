package com.example.studygroup.Service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

    private final User user; // DB에서 조회한 User 엔티티

    // ... 생성자 ...

    public Long getUserId() {
        return user.getId();
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // 로그인 ID로 email을 사용
    }

    // ... 기타 UserDetails 메소드 구현 ...
}
