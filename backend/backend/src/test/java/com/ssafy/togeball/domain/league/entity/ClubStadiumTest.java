package com.ssafy.togeball.domain.league.entity;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ClubStadiumTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void clubStadiumLazyLoadingTest() { // Club, Stadium 객체를 Lazy Loading으로 가져오는지 확인

        //테스트 데이터 생성
        List<Club> clubs = new ArrayList<>();
        Club club1 = new Club("LG","트윈스","logo.png", 1);
        Club club2 = new Club("두산","베어스","logo.png", 5);
        clubs.add(club1);
        clubs.add(club2);
        Stadium stadium1 = new Stadium(clubs, "잠실");
        entityManager.persist(club1);
        entityManager.persist(club2);
        entityManager.persist(stadium1);

        ClubStadium clubStadium1 = ClubStadium.builder().club(club1).stadium(stadium1).build();
        ClubStadium clubStadium2 = ClubStadium.builder().club(club2).stadium(stadium1).build();
        entityManager.persist(clubStadium1);
        entityManager.persist(clubStadium2);

        // 영속성 컨텍스트 초기화
        entityManager.flush();
        entityManager.clear();

        // 엔티티 조회
        ClubStadium found = entityManager.find(ClubStadium.class, clubStadium1.getId());

        // 지연 로딩 검증
        assertNotNull(found);
        assertNotNull(found.getClub());
        assertNotNull(found.getStadium());

        assertFalse(Hibernate.isInitialized(found.getClub()));
        assertFalse(Hibernate.isInitialized(found.getStadium()));

        // 지연 로딩 동작 확인
        assertDoesNotThrow(() -> {
            Stadium stadium2 = found.getStadium();
            System.out.println("stadium name : " + stadium2.getName());
        });
    }
}
