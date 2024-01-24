package com.ssafy.togeball.domain.matching.entity;

import com.ssafy.togeball.domain.chatroom.entity.MatchingChatroom;
import com.ssafy.togeball.domain.tag.entity.MatchingTag;
import com.ssafy.togeball.domain.tag.entity.Tag;
import com.ssafy.togeball.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "TBL_MATCHING")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = {"matchingChatroom", "matchingUser", "matchingTag"})
public class Matching {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matching_id")
    private Integer id;

    private String title;

    private Integer capacity;

    @OneToOne(mappedBy = "matching", cascade = CascadeType.PERSIST)
    private MatchingChatroom matchingChatroom;

    @OneToMany(mappedBy = "matching", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchingUser> matchingUser = new ArrayList<>();

    @OneToMany(mappedBy = "matching", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MatchingTag> matchingTag = new ArrayList<>();

    public void addUser(User user) {
        MatchingUser matchingUser = MatchingUser.builder()
                .matching(this)
                .user(user)
                .build();
        this.matchingUser.add(matchingUser);
    }

    public void addTag(Tag tag) {
        MatchingTag matchingTag = MatchingTag.builder()
                .matching(this)
                .tag(tag)
                .build();
        this.matchingTag.add(matchingTag);
    }

    @Builder
    public Matching(MatchingChatroom matchingChatroom, String title, Integer capacity) {
        this.matchingChatroom = matchingChatroom;
        this.title = title;
        this.capacity = capacity;
    }
}
