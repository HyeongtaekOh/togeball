package com.ssafy.togeball.domain.user.dto;

import com.ssafy.togeball.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {
    Integer id;
    String email;
    String password;
    String nickname;
    String gender;
    LocalDateTime birthdate;
    String phone;
    String profileImage;

    public static UserResponse of(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .gender(user.getGender().toString())
                .birthdate(user.getBirthdate())
                .phone(user.getPhone())
                .profileImage(user.getProfileImage())
                .build();
    }
}
