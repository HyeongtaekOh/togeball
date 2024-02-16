package com.ssafy.togeball.domain.chatroom.entity;

import com.ssafy.togeball.domain.matching.entity.Matching;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@DiscriminatorValue("MATCHING")
@Table(name = "TBL_MATCHING_CHATROOM")
@ToString(callSuper = true, exclude = "matching")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchingChatroom extends Chatroom {

    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matching_id")
    private Matching matching;

    @Builder
    public MatchingChatroom(String title, Integer capacity) {
        this.title = title;
        this.capacity = capacity;
    }
}
