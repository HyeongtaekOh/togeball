package com.ssafy.togeball.domain.league.entity;

import com.ssafy.togeball.domain.league.repository.ClubRepository;
import com.ssafy.togeball.domain.league.repository.GameRepository;
import com.ssafy.togeball.domain.league.repository.StadiumRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
public class LeagueRelationTest { // 양방향 연관관계 테스트

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private StadiumRepository stadiumRepository;

    @Test
    public void relationTest() {
        // Club 생성
        Club homeClub = Club.builder()
                .sponsorName("Home Sponsor")
                .clubName("Home Club")
                .ranking((byte) 1)
                .build();

        Club awayClub = Club.builder()
                .sponsorName("Away Sponsor")
                .clubName("Away Club")
                .ranking((byte) 2)
                .build();

        // Club 저장
        homeClub = clubRepository.save(homeClub);
        awayClub = clubRepository.save(awayClub);

        List<Club> homeClubs = new ArrayList<>();
        homeClubs.add(homeClub);

        // Stadium 생성
        Stadium stadium = Stadium.builder()
                .club(homeClubs)
                .name("S1")
                .fullName("Stadium 1")
                .address("Stadium Address 1")
                .latitude(37.51480219999801)
                .longitude(127.07362609999903)
                .build();

        // Stadium 저장
        stadium = stadiumRepository.save(stadium);

        // Game 생성
        LocalDateTime now = LocalDateTime.now();
        Game game = Game.builder()
                .stadium(stadium)
                .homeClub(homeClub)
                .awayClub(awayClub)
                .datetime(now)
                .build();

        // Game 저장
        game = gameRepository.save(game);

        // Club에 저장된 Games 확인
        homeClub = clubRepository.findById(homeClub.getId()).orElseThrow();
        awayClub = clubRepository.findById(awayClub.getId()).orElseThrow();

        System.out.println("Home Club's Home Games: " + homeClub.getHomeGames());
        System.out.println("Away Club's Away Games: " + awayClub.getAwayGames());

        // Game에 저장된 Clubs 확인
        game = gameRepository.findById(game.getId()).orElseThrow();

        System.out.println("Game's Home Club: " + game.getHomeClub());
        System.out.println("Game's Away Club: " + game.getAwayClub());

        // Stadium에 저장된 Games 확인
        stadium = stadiumRepository.findById(stadium.getId()).orElseThrow();
        System.out.println("Stadium's Games: " + stadium.getGames());

        // Game에 저장된 Stadium 확인
        game = gameRepository.findById(game.getId()).orElseThrow();
        System.out.println("Game's Stadium: " + game.getStadium());
    }
}
