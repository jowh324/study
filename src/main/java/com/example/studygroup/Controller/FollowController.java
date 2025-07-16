package com.example.studygroup.Controller;

import com.example.studygroup.DTO.FollowDto;
import com.example.studygroup.DTO.FollowRequestDto;
import com.example.studygroup.Service.CustomUserDetails;
import com.example.studygroup.Service.FollowService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/follows")
public class FollowController {
    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }


    @PostMapping
    public ResponseEntity<FollowDto> followUser(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody FollowRequestDto request
    ) {
        Long followerId = userDetails.getUserId();
        request.setFollowerId(followerId);

        FollowDto dto = followService.follow(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    @DeleteMapping
    public ResponseEntity<Void> unfollowUser(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody FollowRequestDto request
    ) {
        Long followerId = userDetails.getUserId();
        request.setFollowerId(followerId);

        followService.unfollow(request);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/followers")
    public ResponseEntity<List<FollowDto>> getFollowers(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long currentUserId = userDetails.getUserId();
        return ResponseEntity.ok(followService.listFollowers(currentUserId));
    }

    @GetMapping("/following")
    public ResponseEntity<List<FollowDto>> getFollowing(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long currentUserId = userDetails.getUserId();
        return ResponseEntity.ok(followService.listFollowing(currentUserId));
    }
}
