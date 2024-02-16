package com.ssafy.togeball.domain.user.repository;

import com.ssafy.togeball.domain.user.entity.User;

public interface CustomUserRepository {

    User createUserProxy(Integer userId);
}
