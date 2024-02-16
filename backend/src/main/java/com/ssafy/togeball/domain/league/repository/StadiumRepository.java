package com.ssafy.togeball.domain.league.repository;

import com.ssafy.togeball.domain.league.entity.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StadiumRepository extends JpaRepository<Stadium, Integer> {
}
