package com.ssafy.togeball.domain.tag.repository;

import com.ssafy.togeball.domain.tag.entity.UserTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTagRepository extends JpaRepository<UserTag, Long> {
}
