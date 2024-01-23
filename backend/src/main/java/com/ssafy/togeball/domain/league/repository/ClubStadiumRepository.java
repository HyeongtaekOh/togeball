package com.ssafy.togeball.domain.league.repository;

import com.ssafy.togeball.domain.league.entity.ClubStadium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClubStadiumRepository extends JpaRepository<ClubStadium, Long> {

    Optional<ClubStadium> findByClubIdAndStadiumId(byte clubId, byte stadiumId);

    List<ClubStadium> findByClubId(byte clubId);

    List<ClubStadium> findByStadiumId(byte stadiumId);
}
