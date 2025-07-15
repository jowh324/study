package com.example.studygroup.Entitiy;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;


    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Board> boards = new HashSet<>();

    @Builder
    public Category(String name) {
        this.name = name;

    }
}

