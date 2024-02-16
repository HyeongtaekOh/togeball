package com.ssafy.togeball.domain.chatroom.entity;

import com.ssafy.togeball.domain.common.entity.BaseEntity;
import com.ssafy.togeball.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.HQLSelect;
import org.hibernate.annotations.SQLJoinTableRestriction;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@Table(name = "TBL_CHATROOM")
@DiscriminatorColumn(name = "type")
@SQLRestriction("is_deleted = false")
@ToString(exclude = {"chatroomMemberships"})
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chatroom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private Integer id;

    @Column(nullable = false)
    protected String title;

    @Column(nullable = false, insertable = false, updatable = false)
    protected String type;

    @Column
    protected Integer capacity;

    @OneToMany(mappedBy = "chatroom", cascade = CascadeType.ALL, orphanRemoval = true)
    protected List<ChatroomMembership> chatroomMemberships = new ArrayList<>();

    public void addMember(User member) {
        ChatroomMembership chatroomMembership = ChatroomMembership.builder()
                .chatroom(this)
                .user(member)
                .build();
        chatroomMemberships.add(chatroomMembership);
    }
}

