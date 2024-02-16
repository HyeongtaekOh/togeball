package com.ssafy.togeball.domain.league.entity;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
public class GameTest {

    @Test
    void gameBuilderTest() {

        //Given
        List<Club> clubs = new ArrayList<>();
        Club club1 = new Club("LG","트윈스","logo1.png", 1);
        Club club2 = new Club("두산","베어스","logo2.png", 5);
        clubs.add(club1);
        clubs.add(club2);
        Stadium stadium = new Stadium(clubs, "잠실");
        Club homeClub = new Club("LG","트윈스","logo1.png", 1);
        Club awayClub = new Club("한화","이글스","logo3.png", 9);
        LocalDateTime datetime = LocalDateTime.of(2024,3,23,14,0);

        //When
        Game game = Game.builder()
                .stadium(stadium)
                .homeClub(homeClub)
                .awayClub(awayClub)
                .datetime(datetime)
                .build();

        //Then
        System.out.println("Stadium: " + game.getStadium().getName());
        System.out.println("Home Club: " + game.getHomeClub().getSponsorName());
        System.out.println("Away Club: " + game.getAwayClub().getSponsorName());
        System.out.println("Datetime: " + game.getDatetime());
    }

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void gameLazyLoadingTest() { // Stadium, HomeClub, AwayClub 객체를 Lazy Loading으로 가져오는지 확인

        //테스트 데이터 생성
        List<Club> clubs = new ArrayList<>();
        Club club1 = new Club("LG","트윈스","logo.png", 1);
        Club club2 = new Club("두산","베어스","logo.png", 5);
        clubs.add(club1);
        clubs.add(club2);
        Stadium stadium = new Stadium(clubs, "잠실");
        Club homeClub = new Club("LG","트윈스","logo.png", 1);
        Club awayClub = new Club("한화","이글스","logo.png", 9);
        LocalDateTime datetime = LocalDateTime.of(2024,3,23,14,00);
        entityManager.persist(stadium);
        entityManager.persist(homeClub);
        entityManager.persist(awayClub);

        Game game = Game.builder()
                .stadium(stadium)
                .homeClub(homeClub)
                .awayClub(awayClub)
                .datetime(datetime)
                .build();
        entityManager.persist(game);

        // 영속성 컨텍스트 초기화
        entityManager.clear();

        // 엔티티 조회
        Game found = entityManager.find(Game.class, game.getId()); //getId()가 되나?

        // 지연 로딩 검증
        assertFalse(Hibernate.isInitialized(found.getStadium()));
        assertFalse(Hibernate.isInitialized(found.getHomeClub()));
        assertFalse(Hibernate.isInitialized(found.getAwayClub()));

        // 지연 로딩 동작 확인
        assertDoesNotThrow(() -> {
           Stadium stadium1 = found.getStadium();
           System.out.println("stadium name : " + stadium1.getName());
        });
    }
}
