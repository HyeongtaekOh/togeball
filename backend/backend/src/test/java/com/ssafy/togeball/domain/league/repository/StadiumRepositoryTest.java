package com.ssafy.togeball.domain.league.repository;

import com.ssafy.togeball.domain.league.entity.Club;
import com.ssafy.togeball.domain.league.entity.Stadium;
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

    @Test
    void saveTest() {

        List<Club> clubs = new ArrayList<>();
        Club club1 = new Club("LG","트윈스","logo1.png",1);
        Club club2 = new Club("두산","베어스","logo2.png",5);
        clubs.add(club1);
        clubs.add(club2);
        Stadium stadium = Stadium.builder()
                .clubs(clubs)
                .name("잠실")
                .fullName("잠실종합운동장")
                .address("서울특별시 송파구 올림픽로 25 서울종합운동장")
                .latitude(37.51480219999801)
                .longitude(127.07362609999903)
                .build();
        Stadium saved = stadiumRepository.save(stadium);
        assertNotNull(saved);
        assertEquals(clubs,saved.getClubs());
        assertEquals("잠실",saved.getName());
        assertEquals("잠실종합운동장",saved.getFullName());
        assertEquals("서울특별시 송파구 올림픽로 25 서울종합운동장",saved.getAddress());
        assertEquals(37.51480219999801, saved.getLatitude(), 0.001);
        assertEquals(127.07362609999903,saved.getLongitude(), 0.001);
    }
}
