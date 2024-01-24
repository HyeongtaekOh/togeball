package com.ssafy.togeball.domain.league.repository;

import com.ssafy.togeball.domain.league.entity.ClubStadium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubStadiumRepository extends JpaRepository<ClubStadium, Integer> {

    Optional<ClubStadium> findByClubIdAndStadiumId(Integer clubId, Integer stadiumId);

    List<ClubStadium> findByClubId(Integer clubId);

    List<ClubStadium> findByStadiumId(Integer stadiumId);
}
