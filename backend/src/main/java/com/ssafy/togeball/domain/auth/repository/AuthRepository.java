package com.ssafy.togeball.domain.auth.repository;

import com.ssafy.togeball.domain.auth.entity.Auth;
import com.ssafy.togeball.domain.auth.entity.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Integer> {

    Optional<Auth> findByUserId(Integer userId);

    Optional<Auth> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

    Optional<Auth> findByRefreshToken(String refreshToken);
}
