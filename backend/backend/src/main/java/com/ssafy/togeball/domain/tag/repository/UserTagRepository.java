package com.ssafy.togeball.domain.tag.repository;

import com.ssafy.togeball.domain.tag.entity.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface UserTagRepository extends JpaRepository<UserTag, Integer> {

    Optional<UserTag> findByTagIdAndUserId(Integer tagId, Integer userId);

    Set<UserTag> findByUserId(Integer userId);

    Set<UserTag> findByTagId(Integer tagId);

    void deleteByUserId(Integer userId);
}