package com.ssafy.togeball.domain.league.repository;

import com.ssafy.togeball.domain.league.entity.Club;
import com.ssafy.togeball.domain.league.entity.Game;
import com.ssafy.togeball.domain.league.entity.Stadium;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private StadiumRepository stadiumRepository;

    private Club club1;

    private Club club2;

    private Stadium stadium;

    private Game game1;

    private Game game2;

    void dataInit() {

        List<Club> clubs = new ArrayList<>();
        club1 = Club.builder()
                .sponsorName("LG")
                .clubName("트윈스")
                .logo("logo1.png")
                .ranking(1)
                .build();
        club2 = Club.builder()
                .sponsorName("두산")
                .clubName("베어스")
                .logo("logo2.png")
                .ranking(5)
                .build();
        clubRepository.save(club1);
        clubRepository.save(club2);
        clubs.add(club1);
        clubs.add(club2);

        stadium = Stadium.builder()
                .clubs(clubs)
                .name("잠실")
                .build();
        stadiumRepository.save(stadium);

        LocalDateTime datetime = LocalDateTime.now();
        game1 = Game.builder()
                .stadium(stadium)
                .homeClub(club1)
                .awayClub(club2)
                .datetime(datetime)
                .build();
        game2 = Game.builder()
                .stadium(stadium)
                .homeClub(club2)
                .awayClub(club1)
                .datetime(datetime.plusDays(1))
                .build();
        gameRepository.save(game1);
        gameRepository.save(game2);
    }

    @Test
    void saveTest() {

        // Given
        List<Club> clubs = new ArrayList<>();
        Club club1 = new Club("LG","트윈스","logo1.png", 1);
        Club club2 = new Club("두산","베어스","logo2.png", 5);
        clubs.add(club1);
        clubs.add(club2);
        clubRepository.save(club1);
        clubRepository.save(club2);
        Stadium stadium = new Stadium(clubs, "잠실");
        stadiumRepository.save(stadium);

        Club homeClub = club1;
        Club awayClub = new Club("한화","이글스","logo3.png", 9);
        clubRepository.save(awayClub);

        LocalDateTime datetime = LocalDateTime.of(2024,3,23,14,0);

        Game game = Game.builder()
                .stadium(stadium)
                .homeClub(homeClub)
                .awayClub(awayClub)
                .datetime(datetime)
                .build();

        // When
        Game saved = gameRepository.save(game);

        // Then
        assertNotNull(saved);
        assertEquals(stadium,saved.getStadium());
        assertEquals(homeClub,saved.getHomeClub());
        assertEquals(awayClub,saved.getAwayClub());
        assertEquals(datetime,saved.getDatetime());
    }

    @Test
    void findAllTest() {

        // Given
        dataInit();

        // When
        List<Game> allGames = gameRepository.findAll();

        // Then
        assertEquals(2, allGames.size());
    }

    @Test
    void findBySponsorNameTest() {

        // Given
        dataInit();

        // When
        List<Game> gamesFindBySponsorName = gameRepository.findBySponsorName("LG");

        // Then
        assertEquals(2, gamesFindBySponsorName.size());
    }

    @Test
    void findByClubIdTest() {

        // Given
        dataInit();
        Club saveClub1 = clubRepository.save(club1);

        // When
        List<Game> gamesFindByClubId = gameRepository.findByClubId(saveClub1.getId());

        // Then
        assertEquals(2, gamesFindByClubId.size());
    }
}
