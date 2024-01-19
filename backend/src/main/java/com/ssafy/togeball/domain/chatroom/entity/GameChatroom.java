package com.ssafy.togeball.domain.chatroom.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@DiscriminatorValue("game")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GameChatroom extends Chatroom {

    @Column
    private Long gameId;

    @Builder
    public GameChatroom(String title, Long gameId) {
        this.title = title;
        this.gameId = gameId;
    }
}
