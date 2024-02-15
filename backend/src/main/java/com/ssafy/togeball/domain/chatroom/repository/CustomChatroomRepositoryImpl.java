package com.ssafy.togeball.domain.chatroom.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.togeball.domain.chatroom.dto.GameChatroomRequest;
import com.ssafy.togeball.domain.chatroom.dto.MatchingChatroomRequest;
import com.ssafy.togeball.domain.chatroom.dto.RecruitChatroomRequest;
import com.ssafy.togeball.domain.chatroom.dto.RecruitChatroomSearchCondition;
import com.ssafy.togeball.domain.chatroom.entity.Chatroom;
import com.ssafy.togeball.domain.chatroom.entity.GameChatroom;
import com.ssafy.togeball.domain.chatroom.entity.MatchingChatroom;
import com.ssafy.togeball.domain.chatroom.entity.RecruitChatroom;
import com.ssafy.togeball.domain.chatroom.exception.ChatroomNotFoundException;
import com.ssafy.togeball.domain.common.repository.CustomPagingAndSortingRepository;
import com.ssafy.togeball.domain.league.entity.Club;
import com.ssafy.togeball.domain.league.entity.Game;
import com.ssafy.togeball.domain.league.exception.ClubNotFoundException;
import com.ssafy.togeball.domain.league.exception.GameNotFoundException;
import com.ssafy.togeball.domain.league.repository.ClubRepository;
import com.ssafy.togeball.domain.league.repository.GameRepository;
import com.ssafy.togeball.domain.matching.entity.Matching;
import com.ssafy.togeball.domain.tag.entity.Tag;
import com.ssafy.togeball.domain.tag.exception.TagNotFoundException;
import com.ssafy.togeball.domain.tag.repository.TagRepository;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.exception.UserNotFoundException;
import com.ssafy.togeball.domain.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.ssafy.togeball.domain.chatroom.entity.QChatroom.chatroom;
import static com.ssafy.togeball.domain.chatroom.entity.QChatroomMembership.chatroomMembership;
import static com.ssafy.togeball.domain.chatroom.entity.QRecruitChatroom.recruitChatroom;
import static com.ssafy.togeball.domain.league.entity.QClub.club;
import static com.ssafy.togeball.domain.league.entity.QGame.game;
import static com.ssafy.togeball.domain.league.entity.QStadium.stadium;
import static com.ssafy.togeball.domain.tag.entity.QRecruitTag.recruitTag;
import static com.ssafy.togeball.domain.tag.entity.QTag.tag;
import static com.ssafy.togeball.domain.user.entity.QUser.user;

@Slf4j
@Repository
public class CustomChatroomRepositoryImpl extends CustomPagingAndSortingRepository implements CustomChatroomRepository {

    @PersistenceContext
    private EntityManager em;
    private final JPAQueryFactory queryFactory;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final ClubRepository clubRepository;
    private final TagRepository tagRepository;

    public CustomChatroomRepositoryImpl(EntityManager em, UserRepository userRepository, GameRepository gameRepository, ClubRepository clubRepository, TagRepository tagRepository) {
        this.queryFactory = new JPAQueryFactory(em);
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.clubRepository = clubRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<User> findParticipantsByChatroomId(Integer chatroomId) {

        return queryFactory.select(chatroomMembership.user)
                .from(chatroomMembership)
                .join(chatroomMembership.chatroom, chatroom)
                .where(chatroom.id.eq(chatroomId))
                .fetch();
    }

    @Override
    public Page<Chatroom> findChatroomsByUserId(Integer userId, Pageable pageable) {
        JPAQuery<Chatroom> query = queryFactory.select(chatroom)
                .from(chatroomMembership)
                .join(chatroomMembership.chatroom, chatroom)
                .join(chatroomMembership.user, user)
                .where(user.id.eq(userId)
                        .and(chatroom.type.ne("GAME")));

        return fetchPage(query, pageable);
    }

    @Override
    public Page<RecruitChatroom> findRecruitChatroomsByCondition(RecruitChatroomSearchCondition condition, Pageable pageable) {

        BooleanBuilder builder = new BooleanBuilder();

        log.info("condition: {}", condition);
        if (condition.getGameId() != null) {
            builder.and(recruitChatroom.game.id.eq(condition.getGameId()));
        }
        if (condition.getCheeringClubId() != null) {
            builder.and(recruitChatroom.cheeringClub.id.eq(condition.getCheeringClubId()));
        }
        if (condition.getManagerId() != null) {
            builder.and(recruitChatroom.manager.id.eq(condition.getManagerId()));
        }
        if (condition.getKeyword() != null) {
            BooleanExpression expression = recruitChatroom.title.contains(condition.getKeyword())
                    .or(recruitChatroom.description.contains(condition.getKeyword()));
            builder.and(expression);
        }
        if (condition.getTagIds() != null) {
            builder.and(recruitTag.tag.id.in(condition.getTagIds()));
            JPAQuery<RecruitChatroom> query = queryFactory.selectFrom(recruitChatroom)
                    .join(recruitChatroom.recruitTags, recruitTag)
                    .where(builder)
                    .groupBy(recruitChatroom)
                    .having(recruitChatroom.count().eq((long) condition.getTagIds().size()));

            return fetchPage(query, pageable);
        }

        JPAQuery<RecruitChatroom> query = queryFactory
                .selectFrom(recruitChatroom)
                .where(builder);

        return fetchPage(query, pageable);
    }

    @Override
    public Page<RecruitChatroom> findRecruitChatroomsByManagerId(Integer managerId, Pageable pageable) {
        JPAQuery<RecruitChatroom> query = queryFactory
                .selectFrom(recruitChatroom)
                .leftJoin(recruitChatroom.recruitTags, recruitTag).fetchJoin()
                .leftJoin(recruitTag.tag, tag).fetchJoin()
                .leftJoin(recruitChatroom.manager, user).fetchJoin()
                .leftJoin(recruitChatroom.game, game).fetchJoin()
                .leftJoin(game.stadium, stadium).fetchJoin()
                .where(recruitChatroom.manager.id.eq(managerId));

        return fetchPage(query, pageable);
    }

    @Override
    public boolean addParticipant(Integer chatroomId, Integer userId) {

        int count = em.createNativeQuery("INSERT INTO tbl_chatroom_membership (chatroom_id, user_id) VALUES (?, ?)")
                .setParameter(1, chatroomId)
                .setParameter(2, userId)
                .executeUpdate();
        return count > 0;
    }

    @Override
    public void addParticipants(Integer chatroomId, List<Integer> userIds) {

        for (Integer userId : userIds) {
            addParticipant(chatroomId, userId);
        }
    }

    @Override
    public RecruitChatroom createRecruitChatroom(RecruitChatroomRequest chatroomDto) throws DataIntegrityViolationException {

        User manager = userRepository.findById(chatroomDto.getManagerId())
                .orElseThrow(UserNotFoundException::new);
        Game game = gameRepository.findById(chatroomDto.getGameId())
                .orElseThrow(GameNotFoundException::new);
        Club cheeringClub = clubRepository.findById(chatroomDto.getCheeringClubId())
                .orElseThrow(ClubNotFoundException::new);

        RecruitChatroom recruitChatroom = RecruitChatroom.builder()
                .title(chatroomDto.getTitle())
                .capacity(chatroomDto.getCapacity())
                .description(chatroomDto.getDescription())
                .manager(manager)
                .game(game)
                .cheeringClub(cheeringClub)
                .build();

        List<Tag> tags = tagRepository.findAllById(chatroomDto.getTagIds());
        if (tags.size() != chatroomDto.getTagIds().size()) {
            throw new TagNotFoundException();
        }
        for (Tag tag : tags) {
            recruitChatroom.addTag(tag);
        }
        em.persist(recruitChatroom);
        return recruitChatroom;
    }

    @Override
    public GameChatroom createGameChatroom(GameChatroomRequest chatroomDto) {
        Game game = gameRepository.findById(chatroomDto.getGameId())
                .orElseThrow(GameNotFoundException::new);

        GameChatroom gameChatroom = GameChatroom.builder()
                .title(chatroomDto.getTitle())
                .game(game)
                .build();

        em.persist(gameChatroom);
        return gameChatroom;
    }

    @Override
    public MatchingChatroom createMatchingChatroom(MatchingChatroomRequest chatroomDto) {

        MatchingChatroom matchingChatroom = MatchingChatroom.builder()
                .title(chatroomDto.getTitle())
                .capacity(chatroomDto.getCapacity())
                .build();

        em.persist(matchingChatroom);
        return matchingChatroom;
    }



    @Override
    public RecruitChatroom updateRecruitChatroom(RecruitChatroomRequest chatroomDto) {

        RecruitChatroom chatroom = em.find(RecruitChatroom.class, chatroomDto.getId());
        if (chatroom == null) {
            throw new ChatroomNotFoundException();
        }
        chatroom.changeMetadata(chatroomDto.getTitle(), chatroomDto.getDescription(), chatroomDto.getCapacity());
        if (chatroomDto.getManagerId() != null && !chatroomDto.getManagerId().equals(chatroom.getManager().getId())) {
            User manager = userRepository.findById(chatroomDto.getManagerId())
                    .orElseThrow(UserNotFoundException::new);
            chatroom.changeManager(manager);
        }
        if (chatroomDto.getGameId() != null && !chatroomDto.getGameId().equals(chatroom.getGame().getId())) {
            Game game = gameRepository.findById(chatroomDto.getGameId())
                    .orElseThrow(GameNotFoundException::new);
            chatroom.changeGame(game);
        }
        if (chatroomDto.getCheeringClubId() != null && !chatroomDto.getCheeringClubId().equals(chatroom.getCheeringClub().getId())) {
            Club club = clubRepository.findById(chatroomDto.getCheeringClubId())
                    .orElseThrow(ClubNotFoundException::new);
            chatroom.changeCheeringClub(club);
        }
        if (chatroomDto.getTagIds() != null) {
            updateRecruitChatroomTags(chatroomDto.getId(), chatroomDto.getTagIds());
        }

        return chatroom;
    }

    @Override
    public void updateRecruitChatroomTags(Integer chatroomId, List<Integer> tagIds) {
        RecruitChatroom chatroom = em.find(RecruitChatroom.class, chatroomId);
        if (chatroom == null) {
            throw new ChatroomNotFoundException();
        }
        chatroom.getRecruitTags().clear();
        List<Tag> tags = tagRepository.findAllById(tagIds);
        if (tags.size() != tagIds.size()) {
            throw new TagNotFoundException();
        }
        for (Tag tag : tags) {
            chatroom.addTag(tag);
        }
    }
}
