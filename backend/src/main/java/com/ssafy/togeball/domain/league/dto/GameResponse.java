package com.ssafy.togeball.domain.league.dto;

import com.ssafy.togeball.domain.league.entity.Game;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
public class GameResponse {

    private Integer gameId;
    private Integer stadiumId;
    private String stadiumName;
    private Integer homeClubId;
    private String homeClubName;
    private Integer awayClubId;
    private String awayClubName;
    private LocalDateTime datetime;

    public Game toEntity(GameResponse gameResponse) {
        return Game.builder().build();
    }

    public static GameResponse of(Game game) {
        return GameResponse.builder().build();
    }
}
