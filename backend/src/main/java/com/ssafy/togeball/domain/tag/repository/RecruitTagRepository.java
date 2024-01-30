package com.ssafy.togeball.domain.tag.repository;

import com.ssafy.togeball.domain.tag.entity.RecruitTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

<<<<<<< b1977170c41c643c4a1430d5b6a584654d070d19
=======
import java.util.Collection;
>>>>>>> b8aecc142c7d0a7110a278db37743f17db3754fa
import java.util.List;
import java.util.Optional;

public interface RecruitTagRepository extends JpaRepository<RecruitTag, Integer> {

    @Query("select rt from RecruitTag rt where rt.recruitChatroom.id = :chatroomId and rt.tag.id = :tagId")
    Optional<RecruitTag> findByRecruitChatroomIdAndTagId(Integer chatroomId, Integer tagId);

<<<<<<< b1977170c41c643c4a1430d5b6a584654d070d19
    List<RecruitTag> findAllByRecruitChatroomId(Integer chatroomId);

    void deleteByRecruitChatroomId(Integer chatroomId);
=======
    List<Object> findAllByRecruitChatroomId(Integer recruitChatroomId);
>>>>>>> b8aecc142c7d0a7110a278db37743f17db3754fa
}
