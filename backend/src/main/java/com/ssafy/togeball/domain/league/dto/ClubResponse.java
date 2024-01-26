package com.ssafy.togeball.domain.league.dto;

import com.ssafy.togeball.domain.league.entity.Club;
import com.ssafy.togeball.domain.league.entity.Stadium;
import lombok.Builder;
import lombok.Data;

@Builder
public class ClubResponse {

    private Integer clubId;
    private String sponsorName;
    private String clubName;
    private String logo;
    private Integer ranking;

    public static ClubResponse of(Club club) {
        return ClubResponse.builder()
                .clubId(club.getId())
                .sponsorName(club.getSponsorName())
                .clubName(club.getClubName())
                .logo(club.getLogo())
                .ranking(club.getRanking())
                .build();
    }
}
