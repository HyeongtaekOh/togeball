package com.ssafy.togeball.domain.chatroom.entity;

import com.ssafy.togeball.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "TBL_RECRUITCHATROOM")
@ToString(exclude = {"manager"})
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

    @Builder
    public RecruitChatroom(User manager, String title, String description, Integer capacity) {
        this.manager = manager;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
    }

    public void changeCapacity(Integer capacity) {
        this.capacity = capacity;
    }

}
