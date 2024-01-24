package com.ssafy.togeball.domain.tag.repository;

import com.ssafy.togeball.domain.tag.entity.Tag;
import com.ssafy.togeball.domain.tag.entity.TagType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer>, CustomTagRepository {

    Optional<Tag> findByContent(String content);

    List<Tag> findByType(TagType type);
}
