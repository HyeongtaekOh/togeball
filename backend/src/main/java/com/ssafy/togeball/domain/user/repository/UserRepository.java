package com.ssafy.togeball.domain.user.repository;

import com.ssafy.togeball.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>, CustomUserRepository {

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);
}
