package com.ssafy.togeball.domain.league.repository;

import com.ssafy.togeball.domain.league.entity.Club;
import com.ssafy.togeball.domain.league.entity.ClubStadium;
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
public class ClubStadiumRepositoryTest {

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private StadiumRepository stadiumRepository;

    @Autowired
    private ClubStadiumRepository clubStadiumRepository;

    @Autowired
    private GameRepository gameRepository;

    private Club club;

    private Stadium stadium;

    void dataInit() {

        club = Club.builder()
                .sponsorName("기업명")
                .clubName("클럽명")
                .logo("로고.png")
                .ranking(11)
                .build();
        clubRepository.save(club);
        List<Club> clubs = new ArrayList<>();
        clubs.add(club);
        stadium = Stadium.builder()
                .clubs(clubs)
                .name("Stadium Z")
                .fullName("Full Name Z")
                .address("Address Z")
                .latitude(1.0)
                .longitude(2.0)
                .build();
        stadiumRepository.save(stadium);
    }

    @Test
    void saveTest() {

        // Club 생성 및 저장
        Club homeClub = Club.builder()
                .sponsorName("삼성")
                .clubName("팀명1")
                .logo("logo1.png")
                .ranking(1)
                .build();
        Club savedHomeClub = clubRepository.save(homeClub);
        Club awayClub = Club.builder()
                .sponsorName("애플")
                .clubName("팀명2")
                .logo("logo2.png")
                .ranking(2)
                .build();
        Club savedAwayClub = clubRepository.save(awayClub);

        // List<Club> 생성
        List<Club> homeClubs = new ArrayList<>();
        homeClubs.add(homeClub);
        List<Club> awayClubs = new ArrayList<>();
        awayClubs.add(awayClub);

        // Stadium 생성 및 저장
        Stadium stadiumX = Stadium.builder()
                .clubs(homeClubs)
                .name("Stadium X")
                .fullName("Full Name X")
                .address("Address X")
                .latitude(1.0)
                .longitude(2.0)
                .build();
        Stadium savedStadiumX = stadiumRepository.save(stadiumX);
        Stadium stadiumY = Stadium.builder()
                .clubs(awayClubs)
                .name("Stadium Y")
                .fullName("Full Name Y")
                .address("Address Y")
                .latitude(3.0)
                .longitude(4.0)
                .build();
        Stadium savedStadiumY = stadiumRepository.save(stadiumY);

        // Club-Stadium associations 생성 및 저장
        ClubStadium clubStadium1 = ClubStadium.builder()
                .club(homeClub)
                .stadium(stadiumX)
                .build();
        ClubStadium savedClubStadium1 = clubStadiumRepository.save(clubStadium1);

        ClubStadium clubStadium2 = ClubStadium.builder()
                .club(awayClub)
                .stadium(stadiumY)
                .build();
        ClubStadium savedClubStadium2 = clubStadiumRepository.save(clubStadium2);

        // Game 생성 및 저장
        Game game1 = Game.builder()
                .stadium(stadiumX)
                .homeClub(homeClub)
                .awayClub(awayClub)
                .datetime(LocalDateTime.now())
                .build();
        Game savedGame1 = gameRepository.save(game1);
        Game game2 = Game.builder()
                .stadium(stadiumY)
                .homeClub(awayClub)
                .awayClub(homeClub)
                .datetime(LocalDateTime.now())
                .build();
        Game savedGame2 = gameRepository.save(game2);

        // 검증
        List<Club> allClubs = clubRepository.findAll();
        List<Stadium> allStadiums = stadiumRepository.findAll();
        List<ClubStadium> allClubStadiums = clubStadiumRepository.findAll();
        List<Game> allGames = gameRepository.findAll();
        assertEquals(2, allClubs.size());
        assertEquals(2, allStadiums.size());
        assertEquals(2, allClubStadiums.size());
        assertEquals(2, allGames.size());

        assertEquals("삼성",savedHomeClub.getSponsorName());
        assertEquals("애플",savedAwayClub.getSponsorName());
        assertEquals("Stadium X",savedStadiumX.getName());
        assertEquals("Stadium Y",savedStadiumY.getName());
        assertEquals("삼성",savedClubStadium1.getClub().getSponsorName());
        assertEquals("애플",savedClubStadium2.getClub().getSponsorName());
        assertEquals("Stadium X",savedGame1.getStadium().getName());
        assertEquals("Stadium Y",savedGame2.getStadium().getName());
    }

    @Test
    void testFindByClubIdAndStadiumId() {

        dataInit();
        ClubStadium clubStadium = ClubStadium.builder()
                .club(club)
                .stadium(stadium)
                .build();
        clubStadiumRepository.save(clubStadium);

        assertTrue(clubStadiumRepository.findByClubIdAndStadiumId(club.getId(), stadium.getId()).isPresent());
        assertFalse(clubStadiumRepository.findByClubIdAndStadiumId(club.getId(), 0).isPresent());
    }

    @Test
    void testFindByClubId() {

        dataInit();
        ClubStadium clubStadium = ClubStadium.builder()
                .club(club)
                .stadium(stadium)
                .build();
        clubStadiumRepository.save(clubStadium);

        List<ClubStadium> found = clubStadiumRepository.findByClubId(club.getId());
        assertEquals(1, found.size());
        assertEquals(club.getSponsorName(), found.get(0).getClub().getSponsorName());
    }

    @Test
    void testFindByStadiumId() {

        dataInit();
        ClubStadium clubStadium = ClubStadium.builder()
                .club(club)
                .stadium(stadium)
                .build();
        clubStadiumRepository.save(clubStadium);

        List<ClubStadium> found = clubStadiumRepository.findByStadiumId(stadium.getId());
        assertEquals(1, found.size());
        assertEquals(stadium.getAddress(), found.get(0).getStadium().getAddress());
    }
}
