package com.ssafy.togeball.domain.tag.repository;

import com.ssafy.togeball.domain.tag.entity.Tag;
import com.ssafy.togeball.domain.tag.entity.TagType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @Test
    void tagSaveTest() {

        Tag tag = Tag.builder()
                .content("응원가형")
                .type(TagType.CHEERING_STYLE)
                .build();

        Tag saved = tagRepository.save(tag);

        assertNotNull(saved.getId());

        assertEquals("응원가형", saved.getContent());
        assertEquals(TagType.CHEERING_STYLE, saved.getType());
    }

    @Test
    void tagDuplicateContentTest() {

        String content = "응원석";

        Tag tag1 = Tag.builder()
                .content(content)
                .type(TagType.PREFERRED_SEAT)
                .build();

        tagRepository.save(tag1);

        // 내용과 타입이 모두 같은 태그
        Tag tag2 = Tag.builder()
                .content(content)
                .type(TagType.PREFERRED_SEAT)
                .build();

        assertThrows(DataIntegrityViolationException.class,
                () -> tagRepository.save(tag2));

        // 내용은 같지만, 타입은 다른 태그
        Tag tag3 = Tag.builder()
                .content(content)
                .type(TagType.CUSTOM)
                .build();

        assertThrows(DataIntegrityViolationException.class,
                () -> tagRepository.save(tag3));
    }

    @Test
    void tagFindByContentTest() {

        String content = "잠실구장";
        String notExistingContent = "선릉구장";
        Tag tag = Tag.builder()
                .content(content)
                .type(TagType.PREFERRED_STADIUM)
                .build();
        tagRepository.save(tag);

        Optional<Tag> found = tagRepository.findByContent(content);
        assertTrue(found.isPresent());

        Optional<Tag> notExist = tagRepository.findByContent(notExistingContent);
        assertFalse(notExist.isPresent());
    }

    @Test
    void tagFindByTypeTest() {

        Tag tag1 = Tag.builder()
                .content("도보이동")
                .type(TagType.UNLABELED)
                .build();
        tagRepository.save(tag1);

        Tag tag2 = Tag.builder()
                .content("대중교통")
                .type(TagType.UNLABELED)
                .build();
        tagRepository.save(tag2);

        Tag tag3 = Tag.builder()
                .content("시즌권보유")
                .type(TagType.SEASON_PASS)
                .build();
        tagRepository.save(tag3);

        List<Tag> found = tagRepository.findByType(TagType.UNLABELED);
        assertEquals(2, found.size());
    }

    @Test
    void tagFindAllTest() {

        Tag tag1 = Tag.builder()
                .content("도보이동")
                .type(TagType.UNLABELED)
                .build();
        tagRepository.save(tag1);

        Tag tag2 = Tag.builder()
                .content("대중교통")
                .type(TagType.UNLABELED)
                .build();
        tagRepository.save(tag2);

        Tag tag3 = Tag.builder()
                .content("시즌권보유")
                .type(TagType.SEASON_PASS)
                .build();
        tagRepository.save(tag3);

        List<Tag> found = tagRepository.findAll();
        assertEquals(3, found.size());
    }
}
