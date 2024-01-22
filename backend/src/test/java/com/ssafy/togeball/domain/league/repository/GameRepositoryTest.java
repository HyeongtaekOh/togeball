package com.ssafy.togeball.domain.league.repository;

import com.ssafy.togeball.domain.league.entity.Club;
import com.ssafy.togeball.domain.league.entity.Game;
import com.ssafy.togeball.domain.league.entity.Stadium;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Test
    void GameRegisterTest() {
        List<Club> club = new ArrayList<>();
        Club club1 = new Club("LG","트윈스","Twins",(byte)1);
        Club club2 = new Club("두산","베어스","Bears",(byte)5);
        club.add(club1);
        club.add(club2);
        Stadium stadium = new Stadium(club, "잠실");
        Club homeClub = new Club("LG","트윈스","Twins",(byte)1);
        Club awayClub = new Club("한화","이글스","Eagles",(byte)9);
        LocalDateTime datetime = LocalDateTime.of(2024,3,23,14,00);
        Game game = Game.builder()
                .stadium(stadium)
                .homeClub(homeClub)
                .awayClub(awayClub)
                .datetime(datetime)
                .build();

        assertNotNull(game);
        assertEquals(stadium,game.getStadium());
        assertEquals(homeClub,game.getHomeClub());
        assertEquals(awayClub,game.getAwayClub());
        assertEquals(datetime,game.getDatetime());
    }
}
