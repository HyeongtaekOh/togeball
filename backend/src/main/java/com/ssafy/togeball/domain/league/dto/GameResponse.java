package com.ssafy.togeball.domain.league.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.togeball.domain.league.entity.Game;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class GameResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("stadiumName")
    private String stadiumName;

    @JsonProperty("homeClubName")
    private String homeClubName;

    @JsonProperty("awayClubName")
    private String awayClubName;

    @JsonProperty("homeClubLogo")
    private String homeClubLogo;

    @JsonProperty("awayClubLogo")
    private String awayClubLogo;

    @JsonProperty("datetime")
    private LocalDateTime datetime;

    public static GameResponse of(Game game) {
        return GameResponse.builder()
                .id(game.getId())
                .datetime(game.getDatetime())
                .homeClubName(game.getHomeClub().getSponsorName())
                .awayClubName(game.getAwayClub().getSponsorName())
                .homeClubLogo(game.getHomeClub().getLogo())
                .awayClubLogo(game.getAwayClub().getLogo())
                .stadiumName(game.getStadium().getName())
                .build();
    }
}
