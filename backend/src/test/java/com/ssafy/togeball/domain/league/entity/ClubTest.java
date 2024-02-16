package com.ssafy.togeball.domain.league.entity;

import org.junit.jupiter.api.Test;

public class ClubTest {

    @Test
    void clubBuilderTest() {

        //Given
        String sponsorName = "LG";
        String clubName = "트윈스";
        String logo = "logo.png";
        int ranking = 1;

        //When
        Club club = Club.builder()
                .sponsorName(sponsorName)
                .clubName(clubName)
                .logo(logo)
                .ranking(ranking)
                .build();

        //Then
        System.out.println("Sponsor Name: " + club.getSponsorName());
        System.out.println("Club Name: " + club.getClubName());
        System.out.println("Logo: " + club.getLogo());
        System.out.println("Ranking: " + club.getRanking());
    }
}
