package com.ssafy.togeball.domain.auth.handler;

import com.ssafy.togeball.domain.auth.service.AuthService;
import com.ssafy.togeball.domain.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final AuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        Integer id = extractUsername(authentication);
        String accessToken = jwtService.createAccessToken(id);
        String refreshToken = jwtService.createRefreshToken();

        jwtService.sendAccessToken(response, accessToken);
        jwtService.sendRefreshToken(response, refreshToken);

        authService.updateRefreshToken(id, refreshToken);
    }

    private Integer extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info("userDetails : {}", userDetails.getUsername());
        return Integer.parseInt(userDetails.getUsername());
    }
}
