package com.ssafy.togeball.domain.tag.repository;

import com.ssafy.togeball.domain.tag.entity.MatchingTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchingTagRepository extends JpaRepository<MatchingTag, Integer> {
}