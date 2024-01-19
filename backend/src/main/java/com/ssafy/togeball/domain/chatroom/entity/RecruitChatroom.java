package com.ssafy.togeball.domain.chatroom.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@Entity
@DiscriminatorValue("recruit")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitChatroom extends Chatroom {

    @Column
    private String description;

    @Column
    private Integer capacity;

    @Builder
    public RecruitChatroom(String title, String description, Integer capacity) {
        this.title = title;
        this.description = description;
        this.capacity = capacity;
    }
}
