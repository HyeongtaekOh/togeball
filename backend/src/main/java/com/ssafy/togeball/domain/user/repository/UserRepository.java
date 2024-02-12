package com.ssafy.togeball.domain.user.repository;

import com.ssafy.togeball.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>, CustomUserRepository {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userTags ut LEFT JOIN FETCH ut.tag t WHERE u.id = :id")
    Optional<User> findUserWithTagsById(@Param("id") Integer id);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userTags ut LEFT JOIN FETCH ut.tag t LEFT JOIN FETCH u.club c WHERE u.id = :id")
    Optional<User> findUserWithTagsAndClubById(@Param("id") Integer id);

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.userTags ut LEFT JOIN FETCH ut.tag WHERE u.id IN :userIds")
    List<User> findAllWithTagsByIds(@Param("userIds") List<Integer> userIds);
}
