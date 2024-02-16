package com.ssafy.togeball.domain.league.repository;

import com.ssafy.togeball.domain.league.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    @Query("SELECT g FROM Game g WHERE g.homeClub.id = : clubId OR g.awayClub.id = :clubId")
    List<Game> findByClubId(@Param("clubId") Integer clubId);

    @Query("SELECT g FROM Game g WHERE g.homeClub.sponsorName = :sponsorName OR g.awayClub.sponsorName = :sponsorName")
    List<Game> findBySponsorName(@Param("sponsorName") String sponsorName);

    @Query("SELECT g FROM Game g WHERE DATE(g.datetime) BETWEEN :startDate AND :endDate")
    List<Game> findByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT g FROM Game g WHERE DATE(g.datetime)=:date " +
            "AND (g.homeClub.sponsorName=:sponsorName OR g.awayClub.sponsorName=:sponsorName)")
    List<Game> findBySponsorNameAndDate(@Param("date") Date date, @Param("sponsorName") String sponsorName);
}
