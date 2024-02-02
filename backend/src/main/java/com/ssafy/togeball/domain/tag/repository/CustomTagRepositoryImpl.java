package com.ssafy.togeball.domain.tag.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.togeball.domain.tag.dto.TagCountResponse;
import com.ssafy.togeball.domain.tag.entity.QTag;
import com.ssafy.togeball.domain.tag.entity.QUserTag;
import com.ssafy.togeball.domain.tag.entity.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Repository
public class CustomTagRepositoryImpl implements CustomTagRepository {

    @PersistenceContext
    private EntityManager em;
    private final JPAQueryFactory queryFactory;

    public CustomTagRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Tag createTagProxy(Integer tagId) {
        return em.getReference(Tag.class, tagId);
    }

    @Override
    public List<TagCountResponse> findTagsByUserIdsWithCount(Set<Integer> userIds) {
        QTag tag = QTag.tag;
        QUserTag userTag = QUserTag.userTag;

        NumberPath<Long> countAlias = Expressions.numberPath(Long.class, "count");

        List<Tuple> results = queryFactory
                .select(tag, userTag.count().as(countAlias))
                .from(userTag)
                .join(userTag.tag, tag)
                .where(userTag.user.id.in(userIds))
                .groupBy(tag.id)
                .orderBy(countAlias.desc()) // 카운트로 내림차순 정렬
                .fetch();

        return results.stream().map(tuple -> {
            Tag tagEntity = tuple.get(0, Tag.class);
            Long count = tuple.get(1, Long.class);

            assert tagEntity != null;
            assert count != null;

            return TagCountResponse.builder()
                    .id(tagEntity.getId())
                    .content(tagEntity.getContent())
                    .type(tagEntity.getType())
                    .count(count.intValue())
                    .build();
        }).toList();
    }

    @Override
    public Set<Tag> findTagsByUserIds(Set<Integer> userIds) {
        QTag qTag = QTag.tag;
        QUserTag qUserTag = QUserTag.userTag;

        return new HashSet<>(queryFactory
                .select(qUserTag.tag)
                .from(qUserTag)
                .join(qUserTag.tag, qTag)
                .where(qUserTag.user.id.in(userIds))
                .fetch());
    }
}
