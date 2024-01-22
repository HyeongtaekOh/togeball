package com.ssafy.togeball.domain.chatroom.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@ToString(callSuper = true)
@DiscriminatorValue("GAME")
@Table(name = "TBL_GAMECHATROOM")
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
