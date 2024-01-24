package com.ssafy.togeball.domain.chatroom.entity;

import com.ssafy.togeball.domain.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@Table(name = "TBL_CHATROOM")
@DiscriminatorColumn(name = "type")
@ToString(exclude = {"chatroomMemberships"})
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chatroom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private Long id;

    @Column(nullable = false)
    protected String title;

    @Column(nullable = false, insertable = false, updatable = false)
    protected String type;

    @OneToMany(mappedBy = "chatroom", cascade = CascadeType.ALL, orphanRemoval = true)
    protected List<ChatroomMembership> chatroomMemberships = new ArrayList<>();

    public void addChatroomMembership(ChatroomMembership chatroomMembership) {
        chatroomMemberships.add(chatroomMembership);
    }
}
