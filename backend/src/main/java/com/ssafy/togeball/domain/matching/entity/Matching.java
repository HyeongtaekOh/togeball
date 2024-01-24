package com.ssafy.togeball.domain.matching.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "TBL_MATCHING")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Matching {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matching_id")
    private Long id;

    private String title;

    private Integer capacity;

    @OneToMany(mappedBy = "matching", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchingUser> matchingUser = new ArrayList<>();

    @OneToMany(mappedBy = "matching", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchingTag> matchingTag = new ArrayList<>();

    @Builder
    public Matching(String title, Integer capacity) {
        this.title = title;
        this.capacity = capacity;
    }
}
