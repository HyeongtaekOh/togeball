package com.ssafy.togeball.domain.tag.repository;

import com.ssafy.togeball.domain.tag.entity.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserTagRepository extends JpaRepository<UserTag, Integer> {

    Optional<UserTag> findByTagIdAndUserId(Integer tagId, Integer userId);

    List<UserTag> findByUserId(Integer userId);

    List<UserTag> findByTagId(Integer tagId);
}
