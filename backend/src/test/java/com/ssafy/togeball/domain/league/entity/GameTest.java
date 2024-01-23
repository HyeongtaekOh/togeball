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
        List<Club> club = new ArrayList<>();
        Club club1 = new Club("LG","트윈스",(byte)1);
        Club club2 = new Club("두산","베어스",(byte)5);
        club.add(club1);
        club.add(club2);
        Stadium stadium = new Stadium(club, "잠실");
        Club homeClub = new Club("LG","트윈스",(byte)1);
        Club awayClub = new Club("한화","이글스",(byte)9);
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

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void lazyLoadingTest() { // Stadium, HomeClub, AwayClub 객체를 Lazy Loading으로 가져오는지 확인

        List<Club> club = new ArrayList<>();
        Club club1 = new Club("LG","트윈스",(byte)1);
        Club club2 = new Club("두산","베어스",(byte)5);
        club.add(club1);
        club.add(club2);
        Stadium stadium = new Stadium(club, "잠실");
        Club homeClub = new Club("LG","트윈스",(byte)1);
        Club awayClub = new Club("한화","이글스",(byte)9);
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
