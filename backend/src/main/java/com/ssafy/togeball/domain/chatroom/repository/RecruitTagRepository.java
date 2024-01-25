package com.ssafy.togeball.domain.chatroom.repository;

import com.ssafy.togeball.domain.tag.entity.RecruitTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecruitTagRepository extends JpaRepository<RecruitTag, Integer> {

    Optional<RecruitTag> findByRecruitChatroomIdAndTagId(Integer chatroomId, Integer tagId);
}
