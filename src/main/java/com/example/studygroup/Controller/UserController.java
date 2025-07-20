package com.example.studygroup.Controller;

import com.example.studygroup.DTO.LoginRequestDto;
import com.example.studygroup.DTO.LoginResponseDto;
import com.example.studygroup.DTO.SignupRequestDto;
import com.example.studygroup.DTO.SignupResponseDto;
import com.example.studygroup.Service.CustomUserDetails;
import com.example.studygroup.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(
            @Validated @RequestBody SignupRequestDto request
    ) {
        SignupResponseDto response = userService.register(request);
        return ResponseEntity.created(URI.create("/" + response.getId())).body(response);
    }
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMe(@AuthenticationPrincipal CustomUserDetails userDetails) {
        // userDetails에서 현재 로그인된 사용자의 ID를 가져옵니다.
        Long currentUserId = userDetails.getUserId();

        // 서비스의 deleteUser 메소드를 호출하여 해당 사용자를 삭제합니다.
        userService.deleteUser(currentUserId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/me")
    public ResponseEntity<String> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not Logged In");
        }
        // 로그인한 사용자의 이름(우리는 이메일을 사용)을 반환합니다.
        return ResponseEntity.ok(authentication.getName());
    }
}