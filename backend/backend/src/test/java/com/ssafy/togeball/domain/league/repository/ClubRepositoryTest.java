package com.ssafy.togeball.domain.league.repository;

import com.ssafy.togeball.domain.league.entity.Club;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ClubRepositoryTest {

    @Autowired
    private ClubRepository clubRepository;

    void dataInit() {

        Club club1 = Club.builder()
                .sponsorName("LG")
                .clubName("트윈스")
                .logo("logo.png")
                .ranking(1)
                .build();
        clubRepository.save(club1);
    }

    @Test
    void saveTest() {

        Club club = Club.builder()
                .sponsorName("기업명")
                .clubName("클럽명")
                .logo("로고.png")
                .ranking(11)
                .build();
        Club saved = clubRepository.save(club);
        assertNotNull(club);
        assertEquals("기업명",saved.getSponsorName());
        assertEquals("클럽명",saved.getClubName());
        assertEquals((byte)11,saved.getRanking());
    }

    @Test
    void findBySponsorNameTest() {

        dataInit();
        Optional<Club> found = clubRepository.findBySponsorName("LG");
        assertTrue(found.isPresent());
        assertEquals("트윈스", found.get().getClubName());
    }

    @Test
    void notFindBySponsorNameTest() {

        dataInit();
        String sponsorName = "LL";
        Optional<Club> optionalClub = clubRepository.findBySponsorName(sponsorName);
        assertFalse(optionalClub.isPresent());
    }
}
