package com.ssafy.togeball.domain.league.repository;

import com.ssafy.togeball.domain.league.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    @Query("SELECT g FROM Game g WHERE g.homeClub.sponsorName = :sponsorName OR g.awayClub.sponsorName = :sponsorName")
    List<Game> findBySponsorName(@Param("sponsorName") String sponsorName);

    @Query("SELECT g FROM Game g WHERE DATE(g.datetime)=:date")
    List<Game> findByDate(@Param("date") LocalDate date);
}
