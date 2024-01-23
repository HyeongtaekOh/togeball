package com.ssafy.togeball.domain.chatroom.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.togeball.domain.chatroom.entity.QRecruitChatroom;
import com.ssafy.togeball.domain.chatroom.entity.QRecruitTag;
import com.ssafy.togeball.domain.chatroom.entity.RecruitChatroom;
import com.ssafy.togeball.domain.tag.entity.QTag;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
//@RequiredArgsConstructor
public class CustomChatroomRepositoryImpl implements CustomChatroomRepository {

    private final JPAQueryFactory queryFactory;

    public CustomChatroomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<RecruitChatroom> findByTagIds(List<Long> tagIds) {
        return queryFactory.selectFrom(QRecruitChatroom.recruitChatroom)
                .join(QRecruitChatroom.recruitChatroom.recruitTags, QRecruitTag.recruitTag)
                .join(QRecruitTag.recruitTag.tag, QTag.tag)
                .where(QTag.tag.id.in(tagIds))
                .groupBy(QRecruitChatroom.recruitChatroom)
                .having(QRecruitChatroom.recruitChatroom.count().eq((long) tagIds.size()))
                .fetch();

    }
}
