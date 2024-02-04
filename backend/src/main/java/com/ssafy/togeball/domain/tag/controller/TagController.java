package com.ssafy.togeball.domain.tag.controller;

import com.ssafy.togeball.domain.tag.dto.TagCountResponse;
import com.ssafy.togeball.domain.tag.dto.TagCreateRequest;
import com.ssafy.togeball.domain.tag.dto.TagResponse;
import com.ssafy.togeball.domain.tag.service.TagService;
import com.ssafy.togeball.domain.user.dto.UserIdsRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hashtags")
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<?> findAllTags(Pageable pageable) {
        Page<TagResponse> tags = tagService.findAllTags(pageable).map(TagResponse::of);
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/{tagId}")
    public ResponseEntity<?> findTagById(@PathVariable(value = "tagId") Integer tagId) {
        TagResponse tag = TagResponse.of(tagService.findTagById(tagId));
        return ResponseEntity.ok(tag);
    }

    @GetMapping("/content/{content}")
    public ResponseEntity<?> findTagByContent(@PathVariable(value = "content") String content) {
        TagResponse tag = TagResponse.of(tagService.findTagByContent(content));
        return ResponseEntity.ok(tag);
    }

    @GetMapping("/users/counts")
    public ResponseEntity<?> findTagsByUserIdsWithCount(@RequestBody UserIdsRequest userIdsRequest) {
        List<TagCountResponse> tags = tagService.findTagsByUserIdsWithCount(userIdsRequest.getUserIds());
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/users")
    public ResponseEntity<?> findTagsByUserIds(@RequestBody UserIdsRequest userIdsRequest) {
        Set<TagResponse> tags = tagService.findAllTagsByUserIds(userIdsRequest.getUserIds())
                .stream()
                .map(TagResponse::of)
                .collect(Collectors.toSet());
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/keyword")
    public ResponseEntity<?> findTagsByKeyword(@RequestParam(value = "keyword") String keyword) {
        List<TagResponse> tags = tagService.findTagsStartingWithKeyword(keyword)
                .stream()
                .map(TagResponse::of)
                .toList();
        return ResponseEntity.ok(tags);
    }

    @PostMapping
    public ResponseEntity<?> createCustomTag(@RequestBody TagCreateRequest tagCreateRequest) {
        Integer tagId = tagService.createCustomTag(tagCreateRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{tagId}")
                .buildAndExpand(tagId)
                .toUri();

        return ResponseEntity.created(location).body(Map.of("id", tagId));
    }
}
