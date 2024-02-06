package com.ssafy.togeball.domain.user.repository;

import com.ssafy.togeball.domain.user.entity.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>, CustomUserRepository {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userTags ut LEFT JOIN FETCH ut.tag t WHERE u.id = :id")
    Optional<User> findUserWithTagsById(@Param("id") Integer id);

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);
}