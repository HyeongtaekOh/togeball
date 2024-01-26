package com.ssafy.togeball.domain.chatroom.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.togeball.domain.chatroom.dto.RecruitChatroomRequest;
import com.ssafy.togeball.domain.chatroom.entity.Chatroom;
import com.ssafy.togeball.domain.chatroom.entity.RecruitChatroom;
import com.ssafy.togeball.domain.common.repository.CustomPagingAndSortingRepository;
import com.ssafy.togeball.domain.league.entity.Club;
import com.ssafy.togeball.domain.league.entity.Game;
import com.ssafy.togeball.domain.league.repository.ClubRepository;
import com.ssafy.togeball.domain.league.repository.GameRepository;
import com.ssafy.togeball.domain.tag.entity.Tag;
import com.ssafy.togeball.domain.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
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
    private final GameRepository gameRepository;
    private final ClubRepository clubRepository;

    public CustomChatroomRepositoryImpl(EntityManager em, GameRepository gameRepository, ClubRepository clubRepository) {
        this.queryFactory = new JPAQueryFactory(em);
        this.gameRepository = gameRepository;
        this.clubRepository = clubRepository;
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
    public RecruitChatroom createRecruitChatroom(RecruitChatroomRequest chatroomDto) throws DataIntegrityViolationException {

        User manager = em.getReference(User.class, chatroomDto.getManagerId());
        Game game = em.getReference(Game.class, chatroomDto.getGameId());
        Club cheeringClub = em.getReference(Club.class, chatroomDto.getCheeringClubId());

        RecruitChatroom recruitChatroom = RecruitChatroom.builder()
                .title(chatroomDto.getTitle())
                .description(chatroomDto.getDescription())
                .manager(manager)
                .game(game)
                .cheeringClub(cheeringClub)
                .build();

        for (Integer tagId : chatroomDto.getTagIds()) {
            recruitChatroom.addTag(em.getReference(Tag.class, tagId));
        }
        em.persist(recruitChatroom);
        return recruitChatroom;
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

    @Override
    public RecruitChatroom updateRecruitChatroom(RecruitChatroomRequest chatroomDto) {
        RecruitChatroom chatroom = em.find(RecruitChatroom.class, chatroomDto.getId());
        if (chatroom == null) {
            throw new EntityNotFoundException("존재하지 않는 채팅방 id입니다. id: " + chatroomDto.getId());
        }
        chatroom.changeMetadata(chatroomDto.getTitle(), chatroomDto.getDescription(), chatroomDto.getCapacity());
        updateRecruitChatroomTags(chatroomDto.getId(), chatroomDto.getTagIds());
        if (chatroomDto.getGameId() != null && !chatroomDto.getGameId().equals(chatroom.getGame().getId())) {
            Game game = gameRepository.findById(chatroomDto.getGameId()).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 경기 id입니다. id: " + chatroomDto.getGameId()));
            chatroom.changeGame(game);
        }
        if (chatroomDto.getCheeringClubId() != null && !chatroomDto.getCheeringClubId().equals(chatroom.getCheeringClub().getId())) {
            Club club = clubRepository.findById(chatroomDto.getCheeringClubId()).orElseThrow(() -> new EntityNotFoundException("존재하지 않는 구단 id입니다. id: " + chatroomDto.getCheeringClubId()));
            chatroom.changeCheeringClub(club);
        }
        if (chatroomDto.getTagIds() != null) {
            updateRecruitChatroomTags(chatroomDto.getId(), chatroomDto.getTagIds());
        }

        return chatroom;
    }

    @Override
    public RecruitChatroom updateRecruitChatroomTags(Integer chatroomId, List<Integer> tagIds) {
        RecruitChatroom chatroom = em.find(RecruitChatroom.class, chatroomId);
        if (chatroom == null) {
            throw new EntityNotFoundException("존재하지 않는 채팅방 id입니다. id: " + chatroomId);
        }
        chatroom.getRecruitTags().clear();
        for (Integer tagId : tagIds) {
            chatroom.addTag(em.getReference(Tag.class, tagId));
        }
        return chatroom;
    }

    @Override
    public RecruitChatroom changeRecruitChatroomManager(Integer chatroomId, Integer managerId) {
        RecruitChatroom chatroom = em.find(RecruitChatroom.class, chatroomId);
        if (chatroom == null) {
            throw new IllegalArgumentException("존재하지 않는 채팅방입니다.");
        }
        chatroom.changeManager(em.getReference(User.class, managerId));
        return chatroom;
    }
}
