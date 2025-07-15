package com.example.studygroup.DTO;

public class FollowRequestDto {
    private Long followerId;
    private Long followeeId;

    public FollowRequestDto() {}
    public Long getFollowerId() { return followerId; }
    public void setFollowerId(Long followerId) { this.followerId = followerId; }
    public Long getFolloweeId() { return followeeId; }
    public void setFolloweeId(Long followeeId) { this.followeeId = followeeId; }
}