package com.ssafy.togeball.domain.user.service;

import com.ssafy.togeball.domain.user.dto.UserSignUpRequest;
import com.ssafy.togeball.domain.auth.service.AuthService;
import com.ssafy.togeball.domain.tag.service.TagService;
import com.ssafy.togeball.domain.user.dto.UserResponse;
import com.ssafy.togeball.domain.user.entity.Role;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final TagService tagService;

    public Integer signUp(UserSignUpRequest userSignUpRequest) {
        // TODO :  예외 처리
        if (userRepository.findByEmail(userSignUpRequest.getEmail()).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        if (userRepository.findByNickname(userSignUpRequest.getNickname()).isPresent()) {
            throw new RuntimeException("이미 존재하는 닉네임입니다.");
        }

        User user = User.builder()
                .email(userSignUpRequest.getEmail())
                .nickname(userSignUpRequest.getNickname())
                .role(Role.BASIC)
                .build();

        Integer userId = userRepository.save(user).getId();
        String encodedPassword = passwordEncoder.encode(userSignUpRequest.getPassword());

        authService.createAuth(userId, encodedPassword);
        return userId;
    }

    public UserResponse findUserById(Integer userId) {
        return userRepository.findById(userId)
                .map(UserResponse::of)
                .orElse(null);
    }

    public void updateUserTags(Integer userId, Set<Integer> tagIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 유저가 존재하지 않습니다."));
        tagService.updateUserTags(user, tagIds);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
