package com.ssafy.togeball.domain.auth.controller;

import com.ssafy.togeball.domain.auth.dto.TokenRefreshRequest;
import com.ssafy.togeball.domain.auth.dto.TokenRefreshResponse;
import com.ssafy.togeball.domain.auth.entity.Auth;
import com.ssafy.togeball.domain.auth.repository.AuthRepository;
import com.ssafy.togeball.domain.auth.service.AuthService;
import com.ssafy.togeball.domain.security.jwt.JwtService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            authService.reissueAccessToken(request, response);
            return ResponseEntity.ok().body("액세스 토큰 재발급 완료");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        authService.logout(request);
        return ResponseEntity.ok().body("로그아웃 완료");
    }
}
