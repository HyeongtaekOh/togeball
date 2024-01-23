package com.ssafy.togeball.domain.chatroom.entity;

import com.ssafy.togeball.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "TBL_RECRUITCHATROOM")
@ToString(exclude = {"manager", "recruitTags"})
@DiscriminatorValue("RECRUIT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitChatroom extends Chatroom {

    @Column
    private String description;

    @Column(nullable = false)
    private Integer capacity;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "manager_id", nullable = false)
    private User manager;

    @OneToMany(mappedBy = "recruitChatroom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecruitTag> recruitTags = new ArrayList<>();

    @Builder
    public RecruitChatroom(User manager, String title, String description, Integer capacity) {
        this.manager = manager;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
    }

    public void addRecruitTag(RecruitTag recruitTag) {
        recruitTags.add(recruitTag);
    }

    public void changeCapacity(Integer capacity) {
        this.capacity = capacity;
    }

}
