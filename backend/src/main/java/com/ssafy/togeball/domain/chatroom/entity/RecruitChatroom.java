package com.ssafy.togeball.domain.chatroom.entity;

import com.ssafy.togeball.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "TBL_RECRUITCHATROOM")
@ToString(exclude = {"manager"})
@DiscriminatorValue("recruit")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitChatroom extends Chatroom {

    @Column
    private String description;

    @Column(nullable = false)
    private Integer capacity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private User manager;

    @Builder
    public RecruitChatroom(String title, String description, Integer capacity) {
        this.title = title;
        this.description = description;
        this.capacity = capacity;
    }

    public void changeCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

}
