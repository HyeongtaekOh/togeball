package com.ssafy.togeball.domain.league.repository;

import com.ssafy.togeball.domain.league.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    @Query("SELECT g FROM Game g WHERE DATE(g.datetime)=:date")
    List<Game> findByDate(LocalDate date);

//    List<Game> FindByClubName(String clubName);
}
