package com.ssafy.togeball.domain.auth.service;

import com.ssafy.togeball.domain.auth.entity.Auth;
import com.ssafy.togeball.domain.auth.repository.AuthRepository;
import com.ssafy.togeball.domain.security.jwt.JwtService;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public void saveAuth(User user) {
        Auth auth = Auth.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .build();

        authRepository.save(auth);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // TODO : 예외 관련 처리 필요
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("해당 이메일이 존재하지 않습니다."));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }

    public void reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> refreshTokenOptional = jwtService.extractRefreshToken(request);

        refreshTokenOptional.ifPresent(refreshToken -> {
            if (jwtService.isTokenValid(refreshToken)) {
                authRepository.findByRefreshToken(refreshToken).ifPresent(auth -> {
                    String newAccessToken = jwtService.createAccessToken(auth.getEmail());
                    jwtService.sendAccessToken(response, newAccessToken);
                });
            } else {
                throw new EntityNotFoundException("Invalid refresh token");
            }
        });
    }

    public void logout(HttpServletRequest request) {
        Optional<String> refreshTokenOptional = jwtService.extractRefreshToken(request);

        refreshTokenOptional.flatMap(authRepository::findByRefreshToken).ifPresent(auth -> {
            auth.setRefreshToken(null);
            authRepository.save(auth);
        });
    }
}
