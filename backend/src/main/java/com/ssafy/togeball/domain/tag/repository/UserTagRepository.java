package com.ssafy.togeball.domain.tag.repository;

import com.ssafy.togeball.domain.tag.entity.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserTagRepository extends JpaRepository<UserTag, Integer> {

    @Query("select ut from UserTag ut where ut.tag.id = :tagId and ut.user.id = :userId")
    Optional<UserTag> findByTagIdAndUserId(Integer tagId, Integer userId);

    List<UserTag> findByUserId(Integer userId);

    List<UserTag> findByTagId(Integer tagId);

    @Query("select ut from UserTag ut where ut.user.id in :userIds")
    List<UserTag> findByUserIdIn(Set<Integer> userIds);

    void deleteByUserId(Integer userId);
}