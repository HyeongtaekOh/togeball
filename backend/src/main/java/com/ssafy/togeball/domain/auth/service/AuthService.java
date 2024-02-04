package com.ssafy.togeball.domain.auth.service;

import com.ssafy.togeball.domain.auth.entity.Auth;
import com.ssafy.togeball.domain.auth.repository.AuthRepository;
import com.ssafy.togeball.domain.common.exception.ApiException;
import com.ssafy.togeball.domain.security.jwt.JwtService;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.exception.UserErrorCode;
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

    public void createAuth(Integer userId, String password) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

        Auth auth = Auth.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .password(password)
                .build();

        authRepository.save(auth);
    }

    public Optional<Auth> findAuthByEmail(String email) {
        return authRepository.findByEmail(email);
    }

    public void updateRefreshToken(String email, String refreshToken) {
        authRepository.findByEmail(email)
                .ifPresent(auth -> {
                    auth.setRefreshToken(refreshToken);
                    authRepository.saveAndFlush(auth);
                });
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("해당 이메일이 존재하지 않습니다."));

        Auth auth = authRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("해당 이메일이 존재하지 않습니다."));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(auth.getPassword())
                .roles(user.getRole().name())
                .build();
    }

    public void reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {
        // TODO : 예외 재설정 필요
        String refreshToken = jwtService.extractRefreshToken(request).orElseThrow(EntityNotFoundException::new);

        if (jwtService.isTokenValid(refreshToken)) {
            authRepository.findByRefreshToken(refreshToken).ifPresent(auth -> {
                String newAccessToken = jwtService.createAccessToken(auth.getEmail());
                jwtService.sendAccessToken(response, newAccessToken);
            });
        } else {
            throw new EntityNotFoundException("Invalid refresh token");
        }
    }

    public void setTokensForUser(Integer userId, HttpServletResponse response) {
        Auth auth = authRepository.findByUserId(userId).orElseThrow();
        String email = auth.getEmail();

        String accessToken = jwtService.createAccessToken(email);
        String refreshToken = jwtService.createRefreshToken();

        jwtService.sendAccessToken(response, accessToken);
        jwtService.sendRefreshToken(response, refreshToken);

        updateRefreshToken(email, refreshToken);
    }

    public void logout(HttpServletRequest request) {
        // TODO : 예외 재설정 필요
        String accessToken = jwtService.extractAccessToken(request).orElseThrow(EntityNotFoundException::new);
        String email = jwtService.extractEmail(accessToken).orElseThrow(EntityNotFoundException::new);
        Auth auth = authRepository.findByEmail(email).orElseThrow(EntityNotFoundException::new);
        auth.setRefreshToken(null);
        authRepository.save(auth);
    }
}
