package com.ssafy.togeball.domain.chatroom.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.togeball.domain.chatroom.entity.Chatroom;
import com.ssafy.togeball.domain.chatroom.entity.RecruitChatroom;
import com.ssafy.togeball.domain.chatroom.entity.QRecruitChatroom;
import com.ssafy.togeball.domain.common.repository.CustomPagingAndSortingRepository;
import com.ssafy.togeball.domain.tag.entity.QRecruitTag;
import com.ssafy.togeball.domain.tag.entity.QTag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.togeball.domain.chatroom.entity.QChatroom.chatroom;
import static com.ssafy.togeball.domain.chatroom.entity.QChatroomMembership.chatroomMembership;
import static com.ssafy.togeball.domain.chatroom.entity.QRecruitChatroom.recruitChatroom;
import static com.ssafy.togeball.domain.tag.entity.QRecruitTag.recruitTag;
import static com.ssafy.togeball.domain.tag.entity.QTag.tag;
import static com.ssafy.togeball.domain.user.entity.QUser.user;

@Slf4j
@Repository
public class CustomChatroomRepositoryImpl extends CustomPagingAndSortingRepository implements CustomChatroomRepository {

    @PersistenceContext
    private EntityManager em;
    private final JPAQueryFactory queryFactory;

    public CustomChatroomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public void addUser(Integer chatroomId, Integer userId) {
        em.createNativeQuery("INSERT INTO tbl_chatroom_membership (chatroom_id, user_id) VALUES (?, ?)")
                .setParameter(1, chatroomId)
                .setParameter(2, userId)
                .executeUpdate();
    }

    @Override
    public void addAllUsers(Integer chatroomId, List<Integer> userIds) {
        for (Integer userId : userIds) {
            addUser(chatroomId, userId);
        }
    }

    @Override
    public Page<RecruitChatroom> findByTagIds(List<Integer> tagIds, Pageable pageable) {
        JPAQuery<RecruitChatroom> query = queryFactory.selectFrom(recruitChatroom)
                .join(recruitChatroom.recruitTags, recruitTag)
                .join(recruitTag.tag, tag)
                .where(tag.id.in(tagIds))
                .groupBy(recruitChatroom)
                .having(recruitChatroom.count().eq((long) tagIds.size()));

        return fetchPage(query, pageable);
    }

    @Override
    public Page<Chatroom> findAllChatroomsByUserId(Integer userId, Pageable pageable) {
        JPAQuery<Chatroom> query = queryFactory.select(chatroom)
                .from(chatroomMembership)
                .join(chatroomMembership.chatroom, chatroom)
                .join(chatroomMembership.user, user)
                .where(user.id.eq(userId));

        return fetchPage(query, pageable);
    }

    @Override
    public Page<RecruitChatroom> findAllRecruitChatroomsByManagerId(Integer managerId, Pageable pageable) {
        JPAQuery<RecruitChatroom> query = queryFactory.selectFrom(recruitChatroom)
                .where(recruitChatroom.manager.id.eq(managerId));

        return fetchPage(query, pageable);
    }


}
