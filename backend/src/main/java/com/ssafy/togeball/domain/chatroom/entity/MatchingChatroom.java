package com.ssafy.togeball.domain.chatroom.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "TBL_MATCHINGCHATROOM")
@DiscriminatorValue("matching")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MatchingChatroom extends Chatroom {

    @Column
    private Long matchingId;

    @Builder
    public MatchingChatroom(String title, Long matchingId) {
        this.title = title;
        this.matchingId = matchingId;
    }
}
