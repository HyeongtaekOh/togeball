package com.ssafy.togeball.domain.user.dto;

import com.ssafy.togeball.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {

    private Integer id;
    private String email;
    private String password;
    private String nickname;
    private String gender;
    private LocalDateTime birthdate;
    private String phone;
    private String profileImage;

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
