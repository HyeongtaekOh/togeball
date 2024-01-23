package com.ssafy.togeball.domain.chatroom.repository;

import com.ssafy.togeball.domain.chatroom.entity.RecruitTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecruitTagRepository extends JpaRepository<RecruitTag, Long> {

    Optional<RecruitTag> findByRecruitChatroomIdAndTagId(Long chatroomId, Long tagId);
}
