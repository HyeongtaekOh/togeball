package com.ssafy.togeball.domain.tag.repository;

import com.ssafy.togeball.domain.tag.dto.TagCountResponse;
import com.ssafy.togeball.domain.tag.entity.Tag;

import java.util.List;
import java.util.Set;

public interface CustomTagRepository {

    Tag createTagProxy(Integer tagId);

    List<TagCountResponse> findTagsByUserIdsWithCount(Set<Integer> userIds);

    Set<Tag> findTagsByUserIds(Set<Integer> userIds);
}
