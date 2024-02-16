package com.ssafy.togeball.domain.league.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.togeball.domain.league.entity.Club;
import lombok.Builder;

@Builder
public class ClubResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("sponsorName")
    private String sponsorName;

    @JsonProperty("clubName")
    private String clubName;

    @JsonProperty("logo")
    private String logo;

    @JsonProperty("ranking")
    private Integer ranking;

    public static ClubResponse of(Club club) {
        return ClubResponse.builder()
                .id(club.getId())
                .sponsorName(club.getSponsorName())
                .clubName(club.getClubName())
                .logo(club.getLogo())
                .ranking(club.getRanking())
                .build();
    }
}
