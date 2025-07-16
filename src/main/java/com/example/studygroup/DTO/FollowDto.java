package com.example.studygroup.DTO;

import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class FollowDto {
    private String followerUsername;
    private String followeeUsername;
    private LocalDateTime followedAt;

    public FollowDto() {}
    public FollowDto(String followerUsername, String followeeUsername, LocalDateTime followedAt) {
        this.followerUsername = followerUsername;
        this.followeeUsername = followeeUsername;
        this.followedAt = followedAt;
    }

}