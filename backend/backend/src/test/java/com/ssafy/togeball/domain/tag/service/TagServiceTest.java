package com.ssafy.togeball.domain.tag.service;

import com.ssafy.togeball.domain.chatroom.entity.RecruitChatroom;
import com.ssafy.togeball.domain.matching.entity.Matching;
import com.ssafy.togeball.domain.tag.dto.TagCreateRequest;
import com.ssafy.togeball.domain.tag.entity.*;
import com.ssafy.togeball.domain.tag.repository.MatchingTagRepository;
import com.ssafy.togeball.domain.tag.repository.RecruitTagRepository;
import com.ssafy.togeball.domain.tag.repository.TagRepository;
import com.ssafy.togeball.domain.tag.repository.UserTagRepository;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private TagRepository tagRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserTagRepository userTagRepository;
    @Mock
    private MatchingTagRepository matchingTagRepository;
    @Mock
    private RecruitTagRepository recruitTagRepository;
    @InjectMocks
    private TagService tagService;

    @Test
    void createCustomTag() {
        // given
        Tag tag = Tag.builder().build();

        // ReflectionTestUtils를 사용하여 Tag 객체에 id 설정
        ReflectionTestUtils.setField(tag, "id", 1);

        when(tagRepository.save(any(Tag.class))).thenReturn(tag);

        TagCreateRequest request = new TagCreateRequest();

        // when
        Integer resultTagId = tagService.createCustomTag(request);

        // then
        assertNotNull(resultTagId);
        assertEquals(Integer.valueOf(1), resultTagId);
    }


    @Test
    void addTagToRecruitChatroom() {
        // given
        RecruitChatroom recruitChatroom = RecruitChatroom.builder().build();
        TagCreateRequest request = new TagCreateRequest();
        request.setContent("test");

        Tag tag = request.toEntity();

        // when
        tagService.addTagToRecruitChatroom(recruitChatroom, tag);

        // then
        verify(recruitTagRepository, times(1)).save(any(RecruitTag.class));
    }

    @Test
    void addTagsToMatching() {
        // given
        Matching matching = Matching.builder().build();
        Set<Integer> tagIds = Set.of(1, 2, 3);

        Set<Tag> mockTags = tagIds.stream()
                .map(id -> {
                    Tag t = Tag.builder().build();
                    ReflectionTestUtils.setField(t, "id", id);
                    return t;
                })
                .collect(Collectors.toSet());

        when(tagRepository.findAllByIdIn(tagIds)).thenReturn(mockTags);

        // 캡처를 위한 ArgumentCaptor 설정
        ArgumentCaptor<Set<MatchingTag>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        // when
        tagService.addTagsToMatching(matching, tagIds);

        // then
        verify(tagRepository, times(1)).findAllByIdIn(tagIds);
        verify(matchingTagRepository, times(1)).saveAll(argumentCaptor.capture());

        Set<MatchingTag> capturedUserTags = argumentCaptor.getValue();
        assertEquals(tagIds.size(), capturedUserTags.size());
    }

    @Test
    void updateRecruitChatroomTags() {
        // given
        TagCreateRequest request1 = new TagCreateRequest();
        request1.setContent("test1");
        TagCreateRequest request2 = new TagCreateRequest();
        request2.setContent("test2");

        RecruitChatroom recruitChatroom = RecruitChatroom.builder().build();
        Set<TagCreateRequest> newTags = Set.of(request1, request2);

        // when
        tagService.updateRecruitChatroomTags(recruitChatroom, newTags);

        // then
        verify(recruitTagRepository, times(1)).deleteByRecruitChatroomId(recruitChatroom.getId());
        verify(tagRepository, times(2)).findByContent(anyString());
        verify(tagRepository, times(2)).save(any(Tag.class));
        verify(recruitTagRepository, times(2)).save(any(RecruitTag.class));
    }

    @Test
    void updateUserTags() {
        // given
        Set<Integer> tagIds = Set.of(0, 1, 2);
        User user = User.builder().build();

        Set<Tag> mockTags = tagIds.stream()
                .map(id -> Tag.builder().build())
                .collect(Collectors.toSet());

        when(tagRepository.findAllByIdIn(tagIds)).thenReturn(mockTags);

        // 캡처를 위한 ArgumentCaptor 설정
        ArgumentCaptor<Set<UserTag>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        // when
        tagService.updateUserTags(user, tagIds);

        // then
        verify(userTagRepository, times(1)).deleteByUserId(user.getId());
        verify(tagRepository, times(1)).findAllByIdIn(tagIds);
        verify(userTagRepository, times(1)).saveAll(argumentCaptor.capture());

        Set<UserTag> capturedUserTags = argumentCaptor.getValue();
        assertEquals(tagIds.size(), capturedUserTags.size());
    }

    @Test
    void findTagById() {
        Integer tagId = 1;
        Tag tag = Tag.builder().content("test").type(TagType.UNLABELED).build();

        when(tagRepository.findById(tagId)).thenReturn(Optional.of(tag));

        Tag result = tagService.findTagById(tagId);

        assertEquals("test", result.getContent());
        assertEquals(TagType.UNLABELED, result.getType());
        verify(tagRepository, times(1)).findById(tagId);
    }

    @Test
    void findAllTags() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Tag> tags = List.of(Tag.builder().content("test1").build(), Tag.builder().content("test2").build());
        Page<Tag> page = new PageImpl<>(tags, pageable, tags.size());

        when(tagRepository.findAll(pageable)).thenReturn(page);

        Page<Tag> result = tagService.findAllTags(pageable);

        assertEquals(2, result.getContent().size());
        verify(tagRepository, times(1)).findAll(pageable);
    }

    @Test
    void findAllTagsByUserId() {
        Integer userId = 1;
        Set<UserTag> userTags = generateUserTags();

        when(userRepository.findById(userId)).thenReturn(Optional.of(User.builder().build()));
        when(userTagRepository.findByUserId(userId)).thenReturn(userTags);

        Set<Tag> result = tagService.findAllTagsByUserId(userId);

        assertEquals(userTags.size(), result.size());
        verify(userTagRepository, times(1)).findByUserId(userId);
    }

    @Test
    void findAllTagsByRecruitChatroomId() {
        Integer chatroomId = 1;
        Set<RecruitTag> recruitTags = generateRecruitTags();
        when(recruitTagRepository.findAllByRecruitChatroomId(chatroomId)).thenReturn(recruitTags);

        Set<Tag> result = tagService.findAllTagsByRecruitChatroomId(chatroomId);

        assertEquals(recruitTags.size(), result.size());
        verify(recruitTagRepository, times(1)).findAllByRecruitChatroomId(chatroomId);
    }

    @Test
    void findAllTagsByMatchingId() {
        Integer matchingId = 1;
        Set<MatchingTag> matchingTags = generateMatchingTags();
        when(matchingTagRepository.findAllByMatchingId(matchingId)).thenReturn(matchingTags);

        Set<Tag> result = tagService.findAllTagsByMatchingId(matchingId);

        assertEquals(matchingTags.size(), result.size());
        verify(matchingTagRepository, times(1)).findAllByMatchingId(matchingId);
    }

    @Test
    void findAllTagsByUserIds() {
        Set<Integer> userIds = Set.of(1, 2, 3);
        Set<Tag> tags = generateTags(); // UserTag 객체 생성

        when(tagRepository.findTagsByUserIds(userIds)).thenReturn(tags);

        Set<Tag> result = tagService.findAllTagsByUserIds(userIds);

        assertEquals(tags.size(), result.size());
        verify(tagRepository, times(1)).findTagsByUserIds(userIds);
    }

    Set<Tag> generateTags() {
        return IntStream.range(0, 3)
                .mapToObj(i -> Tag.builder().build())
                .collect(Collectors.toSet());
    }

    Set<UserTag> generateUserTags() {
        return IntStream.range(0, 3)
                .mapToObj(i -> UserTag.builder()
                        .user(User.builder().build())
                        .tag(Tag.builder().build())
                        .build())
                .collect(Collectors.toSet());
    }

    Set<MatchingTag> generateMatchingTags() {
        return IntStream.range(0, 3)
                .mapToObj(i -> MatchingTag.builder()
                        .matching(Matching.builder().build())
                        .tag(Tag.builder().build())
                        .build())
                .collect(Collectors.toSet());
    }

    Set<RecruitTag> generateRecruitTags() {
        return IntStream.range(0, 3)
                .mapToObj(i -> RecruitTag.builder()
                        .recruitChatroom(RecruitChatroom.builder().build())
                        .tag(Tag.builder().build())
                        .build())
                .collect(Collectors.toSet());
    }
}