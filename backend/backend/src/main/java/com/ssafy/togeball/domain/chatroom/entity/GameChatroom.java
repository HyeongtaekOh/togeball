package com.ssafy.togeball.domain.chatroom.entity;

import com.ssafy.togeball.domain.league.entity.Game;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Entity
@ToString(callSuper = true)
@DiscriminatorValue("GAME")
@Table(name = "TBL_GAME_CHATROOM")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GameChatroom extends Chatroom {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @Builder
    public GameChatroom(Game game, String title) {
        this.game = game;
        this.title = title;
        this.capacity = 100;
    }
}
