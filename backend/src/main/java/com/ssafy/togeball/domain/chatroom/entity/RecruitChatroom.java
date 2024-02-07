package com.ssafy.togeball.domain.chatroom.entity;

import com.ssafy.togeball.domain.league.entity.Club;
import com.ssafy.togeball.domain.league.entity.Game;
import com.ssafy.togeball.domain.tag.entity.RecruitTag;
import com.ssafy.togeball.domain.tag.entity.Tag;
import com.ssafy.togeball.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "TBL_RECRUIT_CHATROOM")
@ToString(callSuper = true, exclude = {"manager", "recruitTags"})
@DiscriminatorValue("RECRUIT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitChatroom extends Chatroom {

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", nullable = false)
    private User manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cheering_club_id")
    private Club cheeringClub;

    @OneToMany(mappedBy = "recruitChatroom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecruitTag> recruitTags = new ArrayList<>();

    public void addTag(Tag tag) {
        RecruitTag recruitTag = RecruitTag.builder()
                .recruitChatroom(this)
                .tag(tag)
                .build();
        this.recruitTags.add(recruitTag);
    }

    public void changeMetadata(String title, String description, Integer capacity) {
        this.title = title;
        this.description = description;
        this.capacity = capacity;
    }

    public void changeGame(Game game) {
        this.game = game;
    }

    public void changeCheeringClub(Club cheeringClub) {
        this.cheeringClub = cheeringClub;
    }

    public void changeManager(User manager) {
        this.manager = manager;
    }

    @Builder
    public RecruitChatroom(User manager, Game game, Club cheeringClub, String title, String description, Integer capacity) {
        this.manager = manager;
        this.game = game;
        this.cheeringClub = cheeringClub;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
    }
}
