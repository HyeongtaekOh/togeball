package com.ssafy.togeball.domain.tag.repository;

import com.ssafy.togeball.domain.tag.entity.Tag;
import com.ssafy.togeball.domain.tag.entity.TagType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, Integer>, CustomTagRepository {

    boolean existsByContent(String content);

    Optional<Tag> findByContent(String content);

    Set<Tag> findByType(TagType type);

    Set<Tag> findByContentStartingWith(String keyword);

    Set<Tag> findAllByIdIn(Set<Integer> tagIds);
}
