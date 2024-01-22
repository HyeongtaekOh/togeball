package com.ssafy.togeball.domain.league.repository;

import com.ssafy.togeball.domain.league.entity.Club;
import com.ssafy.togeball.domain.league.entity.Stadium;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class StadiumRepositoryTest {

    @Autowired
    private StadiumRepository stadiumRepository;

    @BeforeEach
    void setUp() {
        List<Club> club = new ArrayList<>();
        Club club1 = new Club("LG","트윈스","Twins",(byte)1);
        Club club2 = new Club("두산","베어스","Bears",(byte)5);
        club.add(club1);
        club.add(club2);

        Stadium stadium1 = Stadium.builder()
                .club(club)
                .name("잠실")
                .fullName("잠실종합운동장")
                .address("서울특별시 송파구 올림픽로 25 서울종합운동장")
                .latitude(37.51480219999801)
                .longitude(127.07362609999903)
                .build();
    }

    @Test
    void saveTest() {
        List<Club> club = new ArrayList<>();
        Club club1 = new Club("기업명","클럽명","Club English Name",(byte)11);
        Stadium stadium = Stadium.builder()
                .club(club)
                .name("구장 약칭")
                .fullName("구장 정식 명칭")
                .address("구장 주소")
                .latitude(37.51480219999801)
                .longitude(127.07362609999903)
                .build();
        Stadium saved = stadiumRepository.save(stadium);
        assertNotNull(saved);
        assertEquals(club,saved.getClub());
        assertEquals("구장 약칭",saved.getName());
        assertEquals("구장 정식 명칭",saved.getFullName());
        assertEquals("구장 주소",saved.getAddress());
        assertEquals(37.51480219999801, saved.getLatitude(), 0.001);
        assertEquals(127.07362609999903,saved.getLongitude(), 0.001);
    }
}
