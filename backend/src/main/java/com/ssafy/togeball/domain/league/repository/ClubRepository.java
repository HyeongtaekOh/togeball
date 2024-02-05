package com.ssafy.togeball.domain.league.repository;

import com.ssafy.togeball.domain.league.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubRepository extends JpaRepository<Club, Integer> {

    Optional<Club> findBySponsorName(@Param("sponsorName") String sponsorName);

    List<Club> findAllByOrderByRankingAsc();
}
