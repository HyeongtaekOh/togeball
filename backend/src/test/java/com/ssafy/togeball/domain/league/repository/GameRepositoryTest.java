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

    @Test
    void saveTest() {

        // Given
        List<Club> clubs = new ArrayList<>();
        Club club1 = new Club("LG","트윈스","logo1.png",(byte)1);
        Club club2 = new Club("두산","베어스","logo2.png",(byte)5);
        clubs.add(club1);
        clubs.add(club2);
        clubRepository.save(club1);
        clubRepository.save(club2);
        Stadium stadium = new Stadium(clubs, "잠실");
        stadiumRepository.save(stadium);

        Club homeClub = club1;
        Club awayClub = new Club("한화","이글스","logo3.png",(byte)9);
        clubRepository.save(awayClub);

        LocalDateTime datetime = LocalDateTime.of(2024,3,23,14,00);

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
        List<Club> clubs = new ArrayList<>();
        Club club1 = new Club("LG","트윈스","logo1.png",(byte)1);
        Club club2 = new Club("두산","베어스","logo2.png",(byte)5);
        clubs.add(club1);
        clubs.add(club2);
        Stadium stadium = new Stadium(clubs, "잠실");
        stadiumRepository.save(stadium);

        Club homeClub = new Club("Home Club", "Home","logoH.png", (byte) 1);
        Club awayClub = new Club("Away Club", "Away","logoA.png", (byte) 2);
        clubRepository.save(homeClub);
        clubRepository.save(awayClub);

        LocalDateTime datetime = LocalDateTime.now();

        Game game1 = Game.builder()
                .stadium(stadium)
                .homeClub(homeClub)
                .awayClub(awayClub)
                .datetime(datetime)
                .build();

        Game game2 = Game.builder()
                .stadium(stadium)
                .homeClub(awayClub)
                .awayClub(homeClub)
                .datetime(datetime.plusDays(1))
                .build();

        gameRepository.save(game1);
        gameRepository.save(game2);

        // When
        List<Game> allGames = gameRepository.findAll();

        // Then
        assertEquals(2, allGames.size());
    }

    @Test
    void findBySponsorNameTest() {

        // Given
        List<Club> clubs = new ArrayList<>();
        Club club1 = new Club("LG","트윈스","logo1.png",(byte)1);
        Club club2 = new Club("두산","베어스","logo2.png",(byte)5);
        clubs.add(club1);
        clubs.add(club2);
        Stadium stadium = new Stadium(clubs, "잠실");
        stadiumRepository.save(stadium);

        Club homeClub = new Club("Home Club", "Home","logoH.png", (byte) 1);
        Club awayClub = new Club("Away Club", "Away","logoA.png", (byte) 2);
        clubRepository.save(homeClub);
        clubRepository.save(awayClub);

        LocalDateTime datetime = LocalDateTime.now();

        Game game1 = Game.builder()
                .stadium(stadium)
                .homeClub(homeClub)
                .awayClub(awayClub)
                .datetime(datetime)
                .build();

        gameRepository.save(game1);

        // When
        List<Game> gamesBySponsorName = gameRepository.findBySponsorName("Home Club");

        // Then
        assertEquals(1, gamesBySponsorName.size());
    }

    //Todo: 날짜 추출 함수를 MariaDB에서는 DATE()를 쓰는데 H2에서는 CAST()를 사용해서 오류 발생
//    @Test
//    void findByDateTest() {
//    }
}
