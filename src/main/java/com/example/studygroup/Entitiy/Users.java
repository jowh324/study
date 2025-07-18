package com.example.studygroup.Entitiy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import lombok.Builder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
@EntityListeners(AuditingEntityListener.class)

public class Users {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    @Column(name="username")
    private String name;
    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;
    @Column(name="created_at")
    @CreatedDate
    private LocalDateTime createdAt;
    @Column(name="updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Board> boards = new HashSet<>();

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Message> sentMessages = new HashSet<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Message> receivedMessages = new HashSet<>();

    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Follow> following = new HashSet<>();

    @OneToMany(mappedBy = "followee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Follow> followers = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StudyPlan> studyPlans = new HashSet<>();
    @Builder
    public Users(Long id, String name,String password, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name=name;
        this.email=email;
        this.password=password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public boolean isAccountNonExpired(){
        // 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않음
    }

    // 계정 잠금 여부 반환

    public boolean isAccountNonLocked(){
        return true; // true -> 잠금되지 않음
    }

    // 패스워드 만료 여부 반환

    public boolean isCredentialsNonExpired(){
        return true; // true -> 만료되지 않음
    }

    // 계정 사용 가능 여부 변환
    public boolean isEnabled(){
        return true;}


}

