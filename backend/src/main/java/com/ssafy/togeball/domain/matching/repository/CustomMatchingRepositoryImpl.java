package com.ssafy.togeball.domain.matching.repository;

import com.ssafy.togeball.domain.chatroom.entity.MatchingChatroom;
import com.ssafy.togeball.domain.matching.dto.MatchingCreateDto;
import com.ssafy.togeball.domain.matching.entity.Matching;
import com.ssafy.togeball.domain.tag.entity.Tag;
import com.ssafy.togeball.domain.user.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomMatchingRepositoryImpl implements CustomMatchingRepository {

    private final EntityManager em;

    /**
     * 매칭 엔티티를 생성하고 영속성 전이를 통해 매칭에 참여하는 유저와의 연결 엔티티와 매칭 채팅방 엔티티를 생성한다.
     */
    @Override
    public Matching createMatchingAndChatroom(MatchingCreateDto matchingDto) {
        Matching matching = matchingDto.toEntity();
        MatchingChatroom matchingChatroom = MatchingChatroom.builder()
                .matching(matching)
                .title(matching.getTitle())
                .build();
        matching.setMatchingChatroom(matchingChatroom);

        for (Integer tagId : matchingDto.getTagIds()) {
            Tag tagProxy = em.getReference(Tag.class, tagId);
            matching.addTag(tagProxy);
        }
        for (Integer userId : matchingDto.getUserIds()) {
            User userProxy = em.getReference(User.class, userId);
            matching.addUser(userProxy);
            matchingChatroom.addMember(userProxy);
        }
        em.persist(matching);
        return matching;
    }
}
