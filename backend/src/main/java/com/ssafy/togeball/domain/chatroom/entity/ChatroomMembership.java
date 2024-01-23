package com.ssafy.togeball.domain.chatroom.entity;


import com.ssafy.togeball.domain.common.entity.AbstractJoinEntity;
import com.ssafy.togeball.domain.common.utils.SimpleTuple;
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
public class ChatroomMembership extends AbstractJoinEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_membership_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    private Chatroom chatroom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    private ChatroomMembership(Chatroom chatroom, User user) {
        this.chatroom = chatroom;
        this.user = user;
    }

    public static ChatroomMembership createChatroomMembership(Chatroom chatroom, User user) {
        ChatroomMembership chatroomMembership = ChatroomMembership.builder()
            .chatroom(chatroom)
            .user(user)
            .build();
        chatroom.addChatroomMembership(chatroomMembership);
        return chatroomMembership;
    }

    @Override
    protected Object getKey() {
        return new SimpleTuple<Long, Long>(chatroom.getId(), user.getId());
    }
}
