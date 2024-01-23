package com.ssafy.togeball.domain.league.repository;

import com.ssafy.togeball.domain.league.entity.Club;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ClubRepositoryTest {

    @Autowired
    private ClubRepository clubRepository;

    @BeforeEach
    void setUp() {
        Club club1 = Club.builder()
                .sponsorName("LG")
                .clubName("트윈스")
                .ranking((byte)1)
                .build();
        clubRepository.save(club1);
    }

    @Test
    void saveTest() {
        Club club = Club.builder()
                .sponsorName("기업명")
                .clubName("클럽명")
                .ranking((byte)11)
                .build();
        Club saved = clubRepository.save(club);
        assertNotNull(club);
        assertEquals("기업명",saved.getSponsorName());
        assertEquals("클럽명",saved.getClubName());
        assertEquals((byte)11,saved.getRanking());
    }

    @Test
    void findBySponsorNameTest() {
        Optional<Club> found = clubRepository.findBySponsorName("LG");
        assertTrue(found.isPresent());
        assertEquals("트윈스", found.get().getClubName());
    }

    @Test
    public void notFindBySponsorNameTest() {
        String sponsorName = "LL";
        Optional<Club> optionalClub = clubRepository.findBySponsorName(sponsorName);
        assertFalse(optionalClub.isPresent());
    }
}
