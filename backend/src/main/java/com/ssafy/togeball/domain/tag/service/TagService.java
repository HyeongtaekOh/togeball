package com.ssafy.togeball.domain.tag.service;

import com.ssafy.togeball.domain.chatroom.entity.RecruitChatroom;
import com.ssafy.togeball.domain.matching.entity.Matching;
import com.ssafy.togeball.domain.tag.dto.TagCreateRequest;
import com.ssafy.togeball.domain.tag.dto.TagResponse;
import com.ssafy.togeball.domain.tag.entity.*;
import com.ssafy.togeball.domain.tag.repository.*;
import com.ssafy.togeball.domain.user.entity.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final UserTagRepository userTagRepository;
    private final MatchingTagRepository matchingTagRepository;
    private final RecruitTagRepository recruitTagRepository;

    // 사용자 정의 태그 추가
    @Transactional
    public Tag createCustomTag(TagCreateRequest request) {
        Tag tag = request.toEntity();
        return tagRepository.save(tag);
    }

    // 모집 채팅방에 태그 추가
    @Transactional
    public void addTagToRecruitChatroom(RecruitChatroom recruitChatroom, TagCreateRequest newTag) {
        Tag tag = tagRepository.findByContent(newTag.getContent())
                .orElseGet(() -> tagRepository.save(
                        newTag.toEntity()
                ));

        RecruitTag recruitTag = RecruitTag.builder()
                .recruitChatroom(recruitChatroom)
                .tag(tag)
                .build();
        recruitTagRepository.save(recruitTag);
    }

    // 매칭에 태그 추가
    @Transactional
    public void addTagsToMatching(Matching matching, Set<Integer> tagIds) {
        List<Tag> tags = tagRepository.findAllById(tagIds);

        List<MatchingTag> matchingTags = tags.stream()
                .map(tag -> MatchingTag.builder()
                        .matching(matching)
                        .tag(tag)
                        .build())
                .collect(Collectors.toList());

        matchingTagRepository.saveAll(matchingTags);
    }


    // 모집 채팅방 태그 수정
    @Transactional
    public void updateRecruitChatroomTags(RecruitChatroom recruitChatroom, Set<TagCreateRequest> newTags) {
        Integer chatroomId = recruitChatroom.getId();

        recruitTagRepository.deleteByRecruitChatroomId(chatroomId);

        newTags.forEach(newTag -> addTagToRecruitChatroom(recruitChatroom, newTag));
    }

    // 회원 해시태그 수정
    @Transactional
    public void updateUserTags(User user, Set<Integer> tagIds) {
        userTagRepository.deleteByUserId(user.getId());

        List<Tag> tags = tagRepository.findAllById(tagIds);
        List<UserTag> userTags = tags.stream()
                .map(tag -> UserTag.builder()
                        .user(user)
                        .tag(tag)
                        .build())
                .toList();
        userTagRepository.saveAll(userTags);
    }

    // id로 태그 찾기
    @Transactional
    public TagResponse findTagById(Integer tagId) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(EntityNotFoundException::new);
        return TagResponse.of(tag);
    }

    // 태그 전체 목록 보기
    @Transactional
    public Page<TagResponse> findAllTags(Pageable pageable) {
        return tagRepository.findAll(pageable)
                .map(TagResponse::of);
    }

    // 해당하는 회원의 태그 목록
    @Transactional
    public Set<TagResponse> findAllTagsByUserId(Integer userId) {
        List<UserTag> userTags = userTagRepository.findByUserId(userId);

        return userTags.stream()
                .map(UserTag::getTag)
                .map(TagResponse::of)
                .collect(Collectors.toSet());
    }

    // 모집 채팅방 id로 태그 목록
    @Transactional
    public Set<TagResponse> findAllTagsByRecruitChatroomId(Integer chatroomId) {
        List<RecruitTag> recruitTags = recruitTagRepository.findAllByRecruitChatroomId(chatroomId);

        return recruitTags.stream()
                .map(RecruitTag::getTag)
                .map(TagResponse::of)
                .collect(Collectors.toSet());
    }

    // 매칭 id로 태그 목록
    @Transactional
    public Set<TagResponse> findAllTagsByMatchingId(Integer matchingId) {
        List<MatchingTag> matchingTags = matchingTagRepository.findAllByMatchingId(matchingId);

        return matchingTags.stream()
                .map(MatchingTag::getTag)
                .map(TagResponse::of)
                .collect(Collectors.toSet());
    }

    // 회원 아이디 목록에 해당하는 태그 목록 구하기
    @Transactional
    public Set<TagResponse> findAllTagsByUserIds(Set<Integer> userIds) {
        List<UserTag> userTags = userTagRepository.findByUserIdIn(userIds);

        return userTags.stream()
                .map(UserTag::getTag)
//                .distinct()
                .map(TagResponse::of)
                .collect(Collectors.toSet());
    }
}
