package com.ssafy.togeball.domain.auth.controller;

import com.ssafy.togeball.domain.auth.dto.TokenRefreshRequest;
import com.ssafy.togeball.domain.auth.dto.TokenRefreshResponse;
import com.ssafy.togeball.domain.auth.entity.Auth;
import com.ssafy.togeball.domain.auth.repository.AuthRepository;
import com.ssafy.togeball.domain.auth.service.AuthService;
import com.ssafy.togeball.domain.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final AuthRepository authRepository;

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        Optional<String> refreshTokenOptional = jwtService.extractRefreshToken(request);

        if (refreshTokenOptional.isPresent()) {
            String refreshToken = refreshTokenOptional.get();
            Optional<Auth> authOptional = authRepository.findByRefreshToken(refreshToken);

            if (authOptional.isPresent()) {
                Auth auth = authOptional.get();
                String newAccessToken = jwtService.createAccessToken(auth.getEmail());
                // TODO : RefreshToken을 매번 재발급 중! 수정 필요
                String newRefreshToken = jwtService.createRefreshToken();

                jwtService.sendAccessAndRefreshToken(response, newAccessToken, newRefreshToken);
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        Optional<String> refreshTokenOptional = jwtService.extractRefreshToken(request);

        if (refreshTokenOptional.isPresent()) {
            String refreshToken = refreshTokenOptional.get();
            authRepository.findByRefreshToken(refreshToken).ifPresent(auth -> {
                auth.setRefreshToken(null);
                authRepository.save(auth);
            });
            return ResponseEntity.ok().body("Logout successful");
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No valid refresh token found");
    }
}
