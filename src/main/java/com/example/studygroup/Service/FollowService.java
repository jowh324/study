package com.example.studygroup.Service;

import com.example.studygroup.DTO.FollowDto;
import com.example.studygroup.DTO.FollowRequestDto;
import com.example.studygroup.Entitiy.Follow;
import com.example.studygroup.Entitiy.FollowId;
import com.example.studygroup.Entitiy.Users;
import com.example.studygroup.Repository.FollowRepository;
import com.example.studygroup.Repository.UserRepository;
import com.example.studygroup.exception.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public FollowService(FollowRepository followRepository, UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public FollowDto follow(FollowRequestDto req) {
        FollowId followId = new FollowId(req.getFollowerId(), req.getFolloweeId());
        if (followRepository.findById(followId).isPresent()) {
            throw new IllegalStateException("You are already following this user.");
        }
        Users follower = userRepository.findById(req.getFollowerId())
                .orElseThrow(() -> new UserNotFoundException(req.getFollowerId()));
        Users followee = userRepository.findById(req.getFolloweeId())
                .orElseThrow(() -> new UserNotFoundException(req.getFolloweeId()));

        Follow follow = new Follow(follower, followee);


        followRepository.save(follow);
        return new FollowDto(follower.getName(), followee.getName(), follow.getFollowedAt());
    }

    @Transactional
    public void unfollow(FollowRequestDto req) {
        FollowId id = new FollowId(req.getFollowerId(), req.getFolloweeId());
        followRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<FollowDto> listFollowers(Long userId) {
        return followRepository.findFollowers(userId).stream()
                .map(f -> new FollowDto(f.getFollower().getName(), f.getFollowee().getName(), f.getFollowedAt()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FollowDto> listFollowing(Long userId) {
        return followRepository.findFollowing(userId).stream()
                .map(f -> new FollowDto(f.getFollower().getName(), f.getFollowee().getName(), f.getFollowedAt()))
                .collect(Collectors.toList());
    }
}
