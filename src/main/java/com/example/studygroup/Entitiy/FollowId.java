package com.example.studygroup.Entitiy;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class FollowId implements java.io.Serializable {
    @Column(name = "following_user_id")
    private Long followerId;

    @Column(name = "followed_user_id")
    private Long followeeId;

    public FollowId() { }

    public FollowId(Long followerId, Long followeeId) {
        this.followerId = followerId;
        this.followeeId = followeeId;
    }
}
