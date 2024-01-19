package com.ssafy.togeball.domain.league.entity;

import org.junit.jupiter.api.Test;

class ClubTest {

    @Test
    void clubBuilderTest() {
        Club club = Club.builder()
                .sponsorName("LG")
                .clubName("트윈스")
                .ranking((byte)1)
                .build();

        System.out.println("club.sponsorName : " + club.getSponsorName());
        System.out.println("club.clubName : " + club.getClubName());
        System.out.println("club.ranking : " + club.getRanking());
    }
}
