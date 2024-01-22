package com.ssafy.togeball.domain.chatroom.entity;


import com.ssafy.togeball.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_CHATROOM_MEMBERSHIP", indexes = {
    @Index(name = "chatroom_membership_idx", columnList = "chatroom_id, user_id")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatroomMembership {

    @Id
    @Column(name = "chatroom_membership_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private Chatroom chatroom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public ChatroomMembership(Chatroom chatroom, User user) {
        this.chatroom = chatroom;
        this.user = user;
    }
}
