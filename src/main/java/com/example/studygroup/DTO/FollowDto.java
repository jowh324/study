package com.example.studygroup.DTO;

import java.time.LocalDateTime;

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
    public String getFollowerUsername() { return followerUsername; }
    public void setFollowerUsername(String followerUsername) { this.followerUsername = followerUsername; }
    public String getFolloweeUsername() { return followeeUsername; }
    public void setFolloweeUsername(String followeeUsername) { this.followeeUsername = followeeUsername; }
    public LocalDateTime getFollowedAt() { return followedAt; }
    public void setFollowedAt(LocalDateTime followedAt) { this.followedAt = followedAt; }
}