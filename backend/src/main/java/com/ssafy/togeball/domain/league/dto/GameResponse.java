package com.ssafy.togeball.domain.league.dto;

import com.ssafy.togeball.domain.league.entity.Game;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
public class GameResponse {

    private Integer id;
    private String stadiumName;
    private String homeClubName;
    private String awayClubName;
    private LocalDateTime datetime;

    public static GameResponse of(Game game) {
        return GameResponse.builder()
                .id(game.getId())
                .datetime(game.getDatetime())
                .homeClubName(game.getHomeClub().getClubName())
                .awayClubName(game.getAwayClub().getClubName())
                .stadiumName(game.getStadium().getName())
                .build();
    }
}
