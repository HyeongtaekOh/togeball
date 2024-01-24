package com.ssafy.togeball.domain.tag.repository;

import com.ssafy.togeball.domain.tag.entity.Tag;

public interface CustomTagRepository {

    Tag createTagProxy(Integer tagId);
}
