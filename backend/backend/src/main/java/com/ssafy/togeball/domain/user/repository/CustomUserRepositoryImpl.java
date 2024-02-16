package com.ssafy.togeball.domain.user.repository;

import com.ssafy.togeball.domain.user.entity.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository {

    private final EntityManager em;

    @Override
    public User createUserProxy(Integer userId) {
        return em.getReference(User.class, userId);
    }
}
