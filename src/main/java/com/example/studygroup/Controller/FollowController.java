package com.example.studygroup.Controller;

import com.example.studygroup.DTO.FollowDto;
import com.example.studygroup.DTO.FollowRequestDto;
import com.example.studygroup.Service.FollowService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<FollowDto> followUser(@Valid @RequestBody FollowRequestDto request) {
        FollowDto dto = followService.follow(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @DeleteMapping
    public ResponseEntity<Void> unfollowUser(@Valid @RequestBody FollowRequestDto request) {
        followService.unfollow(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/followers")
    public ResponseEntity<List<FollowDto>> getFollowers(@RequestParam Long userId) {
        return ResponseEntity.ok(followService.listFollowers(userId));
    }

    @GetMapping("/following")
    public ResponseEntity<List<FollowDto>> getFollowing(@RequestParam Long userId) {
        return ResponseEntity.ok(followService.listFollowing(userId));
    }
}
