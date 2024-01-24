package com.ssafy.togeball.domain.matching.repository;

import com.ssafy.togeball.domain.matching.entity.Matching;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomMatchingRepositoryImpl implements CustomMatchingRepository {

    private final EntityManager em;

    @Override
    public Matching createMatchingProxy(Integer matchingId) {
        return em.getReference(Matching.class, matchingId);
    }
}
