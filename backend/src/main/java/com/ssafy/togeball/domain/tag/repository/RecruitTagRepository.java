package com.ssafy.togeball.domain.tag.repository;

import com.ssafy.togeball.domain.tag.entity.RecruitTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RecruitTagRepository extends JpaRepository<RecruitTag, Integer> {

    @Query("select rt from RecruitTag rt where rt.recruitChatroom.id = :chatroomId and rt.tag.id = :tagId")
    Optional<RecruitTag> findByRecruitChatroomIdAndTagId(Integer chatroomId, Integer tagId);

    List<RecruitTag> findAllByRecruitChatroomId(Integer chatroomId);

    void deleteByRecruitChatroomId(Integer chatroomId);
}
