package com.ssafy.togeball.domain.league.repository;

import com.ssafy.togeball.domain.league.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends JpaRepository<Club, Byte> {
}
