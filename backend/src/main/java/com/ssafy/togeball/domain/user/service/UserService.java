package com.ssafy.togeball.domain.user.service;

import com.ssafy.togeball.domain.auth.dto.UserSignUpRequest;
import com.ssafy.togeball.domain.auth.service.AuthService;
import com.ssafy.togeball.domain.user.entity.Role;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

    public Integer signUp(UserSignUpRequest userSignUpRequest) {

        // TODO : 예외 재설정 필요
        if (userRepository.findByEmail(userSignUpRequest.getEmail()).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        if (userRepository.findByNickname(userSignUpRequest.getNickname()).isPresent()) {
            throw new RuntimeException("이미 존재하는 닉네임입니다.");
        }

        String encodedPassword = passwordEncoder.encode(userSignUpRequest.getPassword());
        User user = User.builder()
                .email(userSignUpRequest.getEmail())
                .password(encodedPassword)
                .nickname(userSignUpRequest.getNickname())
                .role(Role.BASIC)
                .build();

        User saved = userRepository.save(user);
        authService.saveAuth(saved);

        return saved.getId();
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
