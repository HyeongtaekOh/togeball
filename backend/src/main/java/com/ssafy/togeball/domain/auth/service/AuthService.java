package com.ssafy.togeball.domain.auth.service;

import com.ssafy.togeball.domain.auth.entity.Auth;
import com.ssafy.togeball.domain.auth.exception.AuthNotFoundException;
import com.ssafy.togeball.domain.auth.exception.InvalidTokenException;
import com.ssafy.togeball.domain.auth.repository.AuthRepository;
import com.ssafy.togeball.domain.security.jwt.JwtService;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.exception.UserNotFoundException;
import com.ssafy.togeball.domain.user.repository.UserRepository;
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
                .orElseThrow(UserNotFoundException::new);

        Auth auth = Auth.builder()
                .userId(user.getId())
                .password(password)
                .build();

        authRepository.save(auth);
    }

    public Optional<Auth> findAuthById(Integer id) {
        return authRepository.findById(id);
    }

    public Optional<Auth> findByUserId(Integer userId) {
        return authRepository.findByUserId(userId);
    }

    public void updateRefreshToken(Integer id, String refreshToken) {
        authRepository.findByUserId(id)
                .ifPresent(auth -> {
                    auth.setRefreshToken(refreshToken);
                    authRepository.saveAndFlush(auth);
                });
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(Integer.parseInt(username))
                .orElseThrow(UserNotFoundException::new);

        Auth auth = authRepository.findById(user.getId())
                .orElseThrow(AuthNotFoundException::new);

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getId().toString())
                .password(auth.getPassword())
                .roles(user.getRole().name())
                .build();
    }

    public void reissueAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtService.extractRefreshToken(request).orElseThrow(InvalidTokenException::new);

        if (jwtService.isTokenValid(refreshToken)) {
            authRepository.findByRefreshToken(refreshToken).ifPresent(auth -> {
                String newAccessToken = jwtService.createAccessToken(auth.getId());
                jwtService.sendAccessToken(response, newAccessToken);
            });
        } else {
            throw new InvalidTokenException();
        }
    }

    public void setTokensForUser(Integer userId, HttpServletResponse response) {
        Auth auth = authRepository.findByUserId(userId).orElseThrow(AuthNotFoundException::new);
        Integer id = auth.getUserId();

        String accessToken = jwtService.createAccessToken(id);
        String refreshToken = jwtService.createRefreshToken();

        jwtService.sendAccessToken(response, accessToken);
        jwtService.sendRefreshToken(response, refreshToken);

        updateRefreshToken(id, refreshToken);
    }

    public void logout(HttpServletRequest request) {
        String accessToken = jwtService.extractAccessToken(request).orElseThrow(InvalidTokenException::new);
        Integer userId = jwtService.extractId(accessToken).orElseThrow(InvalidTokenException::new);
        Auth auth = authRepository.findByUserId(userId).orElseThrow(AuthNotFoundException::new);
        auth.setRefreshToken(null);
        authRepository.save(auth);
    }
}
