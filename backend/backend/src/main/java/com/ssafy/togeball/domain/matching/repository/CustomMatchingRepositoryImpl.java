package com.ssafy.togeball.domain.matching.repository;

import com.ssafy.togeball.domain.chatroom.dto.MatchingChatroomRequest;
import com.ssafy.togeball.domain.chatroom.entity.MatchingChatroom;
import com.ssafy.togeball.domain.chatroom.repository.ChatroomRepository;
import com.ssafy.togeball.domain.matching.dto.MatchingRequest;
import com.ssafy.togeball.domain.matching.entity.Matching;
import com.ssafy.togeball.domain.tag.entity.Tag;
import com.ssafy.togeball.domain.tag.repository.TagRepository;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomMatchingRepositoryImpl implements CustomMatchingRepository {

    private final EntityManager em;

    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final ChatroomRepository chatroomRepository;

    /**
     * 매칭 엔티티를 생성하고 영속성 전이를 통해 매칭에 참여하는 유저와의 연결 엔티티와 매칭 채팅방 엔티티를 생성한다.
     */
    @Override
    public Matching createMatchingAndChatroom(MatchingRequest matchingDto) {
        Matching matching = matchingDto.toEntity();
        MatchingChatroomRequest matchingChatroomDto = MatchingChatroomRequest.builder()
                .title(matchingDto.getTitle())
                .capacity(matchingDto.getCapacity())
                .build();
        MatchingChatroom matchingChatroom = chatroomRepository.createMatchingChatroom(matchingChatroomDto);
        matching.setMatchingChatroom(matchingChatroom);


        List<Tag> tags = tagRepository.findAllById(matchingDto.getTagIds());
        if (tags.size() != matchingDto.getTagIds().size()) {
            throw new EntityNotFoundException("존재하지 않는 태그가 포함되어 있습니다.");
        }

        List<User> users = userRepository.findAllById(matchingDto.getUserIds());
        if (users.size() != matchingDto.getUserIds().size()) {
            throw new EntityNotFoundException("존재하지 않는 유저가 포함되어 있습니다.");
        }

        for (Tag tag : tags) {
            matching.addTag(tag);
        }
        for (User user : users) {
            matching.addUser(user);
        }
        em.persist(matching);
        matchingChatroom.setMatching(matching);
        return matching;
    }
}
