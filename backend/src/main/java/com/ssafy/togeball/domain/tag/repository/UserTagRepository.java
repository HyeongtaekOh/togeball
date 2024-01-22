package com.ssafy.togeball.domain.tag.repository;

import com.ssafy.togeball.domain.tag.entity.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserTagRepository extends JpaRepository<UserTag, Long> {

    Optional<UserTag> findByTagIdAndUserId(Long tagId, Long userId);

    List<UserTag> findByUserId(Long userId);

    List<UserTag> findByTagId(Long tagId);
}
