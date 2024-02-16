package com.ssafy.togeball.domain.user.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class UserTest {

    @Test
    void userBuilderTest() {
        User user = User.builder()
                .email("email@gmail.com")
                .nickname("nickname")
                .birthdate(LocalDateTime.of(1999, 4, 23, 0, 0, 0))
                .gender(Gender.FEMALE)
                .phone("010-1234-5678")
                .profileImage("profile.jpg")
                .build();

        System.out.println("user.email : " + user.getEmail());
        System.out.println("user.nickname : " + user.getNickname());
        System.out.println("user.birthdate : " + user.getBirthdate());
        System.out.println("user.gender : " + user.getGender());
        System.out.println("user.phone : " + user.getPhone());
        System.out.println("user.profile : " + user.getProfileImage());
    }
}