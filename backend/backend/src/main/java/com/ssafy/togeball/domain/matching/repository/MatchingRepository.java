package com.ssafy.togeball.domain.matching.repository;

import com.ssafy.togeball.domain.matching.entity.Matching;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchingRepository extends JpaRepository<Matching, Integer>, CustomMatchingRepository {

}
