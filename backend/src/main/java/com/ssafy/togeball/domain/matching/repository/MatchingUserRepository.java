package com.ssafy.togeball.domain.matching.repository;

import com.ssafy.togeball.domain.matching.entity.MatchingUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchingUserRepository extends JpaRepository<MatchingUser, Integer> {
}
