package com.ssafy.togeball.domain.tag.service;

import com.ssafy.togeball.domain.chatroom.entity.RecruitChatroom;
import com.ssafy.togeball.domain.matching.entity.Matching;
import com.ssafy.togeball.domain.tag.dto.TagCreateRequest;
import com.ssafy.togeball.domain.tag.dto.TagResponse;
import com.ssafy.togeball.domain.tag.entity.*;
import com.ssafy.togeball.domain.tag.repository.MatchingTagRepository;
import com.ssafy.togeball.domain.tag.repository.RecruitTagRepository;
import com.ssafy.togeball.domain.tag.repository.TagRepository;
import com.ssafy.togeball.domain.tag.repository.UserTagRepository;
import com.ssafy.togeball.domain.user.entity.User;
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
        Tag tag = Tag.builder()
                .content("test")
                .type(TagType.CUSTOM)
                .build();

        when(tagRepository.save(any(Tag.class))).thenReturn(tag);

        TagCreateRequest request = new TagCreateRequest();
        request.setContent("test");

        // when
        Tag result = tagService.createCustomTag(request);

        assertEquals(tag.getContent(), result.getContent());
        assertEquals(tag.getType(), result.getType());
    }

    @Test
    void addTagToRecruitChatroom_WhenTagDoesNotExist() {
        // given
        RecruitChatroom recruitChatroom = RecruitChatroom.builder().build();
        TagCreateRequest request = new TagCreateRequest();
        request.setContent("test");

        when(tagRepository.findByContent("test")).thenReturn(Optional.empty());

        // when
        tagService.addTagToRecruitChatroom(recruitChatroom, request);

        // then
        verify(tagRepository, times(1)).save(any(Tag.class));
        verify(recruitTagRepository, times(1)).save(any(RecruitTag.class));
    }

    @Test
    void addTagToRecruitChatroom_WhenTagExists() {
        // given
        RecruitChatroom recruitChatroom = RecruitChatroom.builder().build();
        TagCreateRequest request = new TagCreateRequest();
        request.setContent("test");
        Tag existingTag = Tag.builder()
                .content("test")
                .build();

        when(tagRepository.findByContent("test")).thenReturn(Optional.of(existingTag));

        // when
        tagService.addTagToRecruitChatroom(recruitChatroom, request);

        // then
        verify(tagRepository, never()).save(any(Tag.class));
        verify(recruitTagRepository, times(1)).save(any(RecruitTag.class));
    }

    @Test
    void addTagsToMatching() {
        // given
        Matching matching = Matching.builder().build();
        Set<Integer> tagIds = Set.of(1, 2, 3);

        // 3개의 태그를 가진 리스트 생성 - 각각이 고유한 content를 갖도록
        List<Tag> mockTags = IntStream.range(0, 3)
                .mapToObj(id -> Tag.builder().content("" + id).build())
                .collect(Collectors.toList());

        when(tagRepository.findAllById(tagIds)).thenReturn(mockTags);

        // 캡처를 위한 ArgumentCaptor 설정
        ArgumentCaptor<List<MatchingTag>> argumentCaptor = ArgumentCaptor.forClass(List.class);

        // when
        tagService.addTagsToMatching(matching, tagIds);

        // then
        verify(tagRepository, times(1)).findAllById(tagIds);
        verify(matchingTagRepository, times(1)).saveAll(argumentCaptor.capture());

        // 캡처한 리스트의 각 태그의 content가 0, 1, 2인지 확인
        List<MatchingTag> capturedMatchingTags = argumentCaptor.getValue();
        IntStream.range(0, capturedMatchingTags.size())
                .forEach(i -> assertEquals("" + i, capturedMatchingTags.get(i).getTag().getContent()));
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

        // 3개의 태그를 가진 리스트 생성 - 각각이 고유한 content를 갖도록
        List<Tag> mockTags = tagIds.stream()
                .map(id -> Tag.builder().content("" + id).build())
                .collect(Collectors.toList());

        // 캡처를 위한 ArgumentCaptor 설정
        when(tagRepository.findAllById(tagIds)).thenReturn(mockTags);

        ArgumentCaptor<List<UserTag>> argumentCaptor = ArgumentCaptor.forClass(List.class);

        // when
        tagService.updateUserTags(user, tagIds);

        // then
        verify(userTagRepository, times(1)).deleteByUserId(user.getId());
        verify(tagRepository, times(1)).findAllById(tagIds);
        verify(userTagRepository, times(1)).saveAll(argumentCaptor.capture());

        // 캡처한 리스트의 각 태그의 content가 0, 1, 2인지 확인
        List<UserTag> capturedUserTags = argumentCaptor.getValue();
        assertEquals(tagIds.size(), capturedUserTags.size());
        IntStream.range(0, capturedUserTags.size())
                .forEach(i -> assertEquals("" + i, capturedUserTags.get(i).getTag().getContent()));
    }

    @Test
    void findTagById() {
        Integer tagId = 1;
        Tag tag = Tag.builder().content("test").type(TagType.UNLABELED).build();

        when(tagRepository.findById(tagId)).thenReturn(Optional.of(tag));

        TagResponse result = tagService.findTagById(tagId);

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

        Page<TagResponse> result = tagService.findAllTags(pageable);

        assertEquals(2, result.getContent().size());
        verify(tagRepository, times(1)).findAll(pageable);
    }

    @Test
    void findAllTagsByUserId() {
        Integer userId = 1;
        List<UserTag> userTags = generateUserTags();

        when(userTagRepository.findByUserId(userId)).thenReturn(userTags);

        Set<TagResponse> result = tagService.findAllTagsByUserId(userId);

        assertEquals(userTags.size(), result.size());
        verify(userTagRepository, times(1)).findByUserId(userId);
    }

    @Test
    void findAllTagsByRecruitChatroomId() {
        Integer chatroomId = 1;
        List<RecruitTag> recruitTags = generateRecruitTags();
        when(recruitTagRepository.findAllByRecruitChatroomId(chatroomId)).thenReturn(recruitTags);

        Set<TagResponse> result = tagService.findAllTagsByRecruitChatroomId(chatroomId);

        assertEquals(recruitTags.size(), result.size());
        verify(recruitTagRepository, times(1)).findAllByRecruitChatroomId(chatroomId);
    }

    @Test
    void findAllTagsByMatchingId() {
        Integer matchingId = 1;
        List<MatchingTag> matchingTags = generateMatchingTags();
        when(matchingTagRepository.findAllByMatchingId(matchingId)).thenReturn(matchingTags);

        Set<TagResponse> result = tagService.findAllTagsByMatchingId(matchingId);

        assertEquals(matchingTags.size(), result.size());
        verify(matchingTagRepository, times(1)).findAllByMatchingId(matchingId);
    }

    @Test
    void findAllTagsByUserIds() {
        Set<Integer> userIds = Set.of(1, 2, 3);
        List<UserTag> userTags = generateUserTags(); // UserTag 객체 생성

        when(userTagRepository.findByUserIdIn(userIds)).thenReturn(userTags);

        Set<TagResponse> result = tagService.findAllTagsByUserIds(userIds);

        assertEquals(userTags.size(), result.size());
        verify(userTagRepository, times(1)).findByUserIdIn(userIds);
    }

    List<UserTag> generateUserTags() {
        return IntStream.range(0, 3)
                .mapToObj(i -> UserTag.builder()
                        .user(User.builder().build())
                        .tag(Tag.builder().build())
                        .build())
                .collect(Collectors.toList());
    }

    List<MatchingTag> generateMatchingTags() {
        return IntStream.range(0, 3)
                .mapToObj(i -> MatchingTag.builder()
                        .matching(Matching.builder().build())
                        .tag(Tag.builder().build())
                        .build())
                .collect(Collectors.toList());
    }

    List<RecruitTag> generateRecruitTags() {
        return IntStream.range(0, 3)
                .mapToObj(i -> RecruitTag.builder()
                        .recruitChatroom(RecruitChatroom.builder().build())
                        .tag(Tag.builder().build())
                        .build())
                .collect(Collectors.toList());
    }
}