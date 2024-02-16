package com.ssafy.togeball.domain.tag.service;

import com.ssafy.togeball.domain.chatroom.entity.RecruitChatroom;
import com.ssafy.togeball.domain.common.exception.ApiException;
import com.ssafy.togeball.domain.matching.entity.Matching;
import com.ssafy.togeball.domain.tag.dto.TagCountResponse;
import com.ssafy.togeball.domain.tag.dto.TagCreateRequest;
import com.ssafy.togeball.domain.tag.entity.*;
import com.ssafy.togeball.domain.tag.exception.TagErrorCode;
import com.ssafy.togeball.domain.tag.exception.TagNotFoundException;
import com.ssafy.togeball.domain.tag.repository.*;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.exception.UserErrorCode;
import com.ssafy.togeball.domain.user.exception.UserNotFoundException;
import com.ssafy.togeball.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final UserTagRepository userTagRepository;
    private final MatchingTagRepository matchingTagRepository;
    private final RecruitTagRepository recruitTagRepository;

    // 사용자 정의 태그 추가
    @Transactional
    public Integer createCustomTag(TagCreateRequest request) {
        if (tagRepository.existsByContent(request.getContent())) {
            throw new ApiException(TagErrorCode.DUPLICATE_TAG);
        }

        Tag tag = request.toEntity();
        return tagRepository.save(tag).getId();
    }

    // 모집 채팅방에 태그 추가
    @Transactional
    public void addTagToRecruitChatroom(RecruitChatroom recruitChatroom, Tag tag) {
        RecruitTag recruitTag = RecruitTag.builder()
                .recruitChatroom(recruitChatroom)
                .tag(tag)
                .build();
        recruitTagRepository.save(recruitTag);
    }

    // 매칭에 태그 추가
    @Transactional
    public void addTagsToMatching(Matching matching, Set<Integer> tagIds) {
        Set<Tag> tags = tagRepository.findAllByIdIn(tagIds);

        Set<MatchingTag> matchingTags = tags.stream()
                .map(tag -> MatchingTag.builder()
                        .matching(matching)
                        .tag(tag)
                        .build())
                        .collect(Collectors.toSet());

        matchingTagRepository.saveAll(matchingTags);
    }

    // 모집 채팅방 태그 수정
    @Transactional
    public void updateRecruitChatroomTags(RecruitChatroom recruitChatroom, Set<TagCreateRequest> newTags) {
        Integer chatroomId = recruitChatroom.getId();

        recruitTagRepository.deleteByRecruitChatroomId(chatroomId);
        recruitTagRepository.flush();

        // 없는 태그일 경우, 추가
        newTags.forEach(newTag -> {
            Tag tag = tagRepository.findByContent(newTag.getContent())
                    .orElseGet(() -> tagRepository.save(
                            newTag.toEntity()
                    ));
            addTagToRecruitChatroom(recruitChatroom, tag);
        });
    }

    // 회원 해시태그 수정
    @Transactional
    public void updateUserTags(User user, Set<Integer> tagIds) {

        userTagRepository.deleteByUserId(user.getId());
        userTagRepository.flush();

        Set<Tag> tags = tagRepository.findAllByIdIn(tagIds);
        if (tags.size() != tagIds.size()) {
            throw new TagNotFoundException();
        }

        Set<UserTag> userTags = tags.stream()
                .map(tag -> UserTag.builder()
                        .user(user)
                        .tag(tag)
                        .build())
                .collect(Collectors.toSet());
        userTagRepository.saveAll(userTags);
    }

    // id로 태그 찾기
    @Transactional
    public Tag findTagById(Integer tagId) {
        return tagRepository.findById(tagId).orElseThrow(TagNotFoundException::new);
    }

    // 내용으로 태그 찾기
    @Transactional
    public Tag findTagByContent(String content) {
        return tagRepository.findByContent(content).orElseThrow(TagNotFoundException::new);
    }

    // keyword로 시작하는 태그 목록
    @Transactional
    public Set<Tag> findTagsStartingWithKeyword(String keyword) {
        return tagRepository.findByContentStartingWith(keyword);
    }

    // 태그 전체 목록 보기
    @Transactional
    public Page<Tag> findAllTags(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    // 해당하는 회원의 태그 목록
    @Transactional
    public Set<Tag> findAllTagsByUserId(Integer userId) {
        userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Set<UserTag> userTags = userTagRepository.findByUserId(userId);

        return userTags.stream()
                .map(UserTag::getTag)
                .collect(Collectors.toSet());
    }

    // 모집 채팅방 id로 태그 목록
    @Transactional
    public Set<Tag> findAllTagsByRecruitChatroomId(Integer chatroomId) {
        Set<RecruitTag> recruitTags = recruitTagRepository.findAllByRecruitChatroomId(chatroomId);

        return recruitTags.stream()
                .map(RecruitTag::getTag)
                .collect(Collectors.toSet());
    }

    // 매칭 id로 태그 목록
    @Transactional
    public Set<Tag> findAllTagsByMatchingId(Integer matchingId) {
        Set<MatchingTag> matchingTags = matchingTagRepository.findAllByMatchingId(matchingId);

        return matchingTags.stream()
                .map(MatchingTag::getTag)
                .collect(Collectors.toSet());
    }

    // 회원 아이디 목록에 해당하는 태그 목록 구하기
    @Transactional
    public Set<Tag> findAllTagsByUserIds(Set<Integer> userIds) {
        return tagRepository.findTagsByUserIds(userIds);
    }

    // 회원 아이디 목록에 해당하는 태그 목록을 개수와 함께
    @Transactional
    public List<TagCountResponse> findTagsByUserIdsWithCount(Set<Integer> userIds) {
        return tagRepository.findTagsByUserIdsWithCount(userIds);
    }
}
