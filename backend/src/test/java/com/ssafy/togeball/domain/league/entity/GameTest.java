package com.ssafy.togeball.domain.league.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GameTest {

    @Test
    void gameBuilderTest() {
        //Given
        List<Club> club = new ArrayList<>();
        Club club1 = new Club("LG","트윈스","Twins",(byte)1);
        Club club2 = new Club("두산","베어스","Bears",(byte)5);
        club.add(club1);
        club.add(club2);
        Stadium stadium = new Stadium(club, "잠실");
        Club homeClub = new Club("LG","트윈스","Twins",(byte)1);
        Club awayClub = new Club("한화","이글스","Eagles",(byte)9);
        LocalDateTime datetime = LocalDateTime.of(2024,3,23,14,00);

        //When
        Game game = Game.builder()
                .stadium(stadium)
                .homeClub(homeClub)
                .awayClub(awayClub)
                .datetime(datetime)
                .build();

        //Then
        System.out.println(game.toString());
    }
}
