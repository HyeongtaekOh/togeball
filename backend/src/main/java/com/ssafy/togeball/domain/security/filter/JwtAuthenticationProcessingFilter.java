package com.ssafy.togeball.domain.security.filter;

import com.ssafy.togeball.domain.auth.entity.Auth;
import com.ssafy.togeball.domain.auth.service.AuthService;
import com.ssafy.togeball.domain.security.exception.JwtAuthenticationException;
import com.ssafy.togeball.domain.security.jwt.JwtService;
import com.ssafy.togeball.domain.user.entity.User;
import com.ssafy.togeball.domain.user.service.UserService;
import com.ssafy.togeball.domain.common.utils.PasswordUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    private static final String NO_CHECK_URL = "/api/auth/login";

    private final JwtService jwtService;
    private final UserService userService;
    private final AuthService authService;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (!request.getRequestURI().equals(NO_CHECK_URL)) {

            Integer userId = jwtService.extractAccessToken(request)
                    .filter(jwtService::isTokenValid)
                    .flatMap(jwtService::extractId)
                    .orElse(null);

            if (userId != null) {
                Optional<User> userOpt = userService.findUserById(userId);
                Optional<Auth> authOpt = authService.findByUserId(userId);

                if (userOpt.isPresent() && authOpt.isPresent()) {
                    saveAuthentication(userOpt.get(), authOpt.get());
                } else {
                    log.warn("User or Auth not found for userId in access token in request to {}", request.getRequestURI());
                }
            } else {
                log.warn("Invalid or missing access token in request to {}", request.getRequestURI());
            }
        }

        filterChain.doFilter(request, response);
    }

    public void saveAuthentication(User myUser, Auth auth) {

        String password = auth.getPassword();
        if (password == null) {
            password = PasswordUtil.generateRandomPassword();
        }

        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
                .username(myUser.getId().toString())
                .password(password)
                .roles(myUser.getRole().name())
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                        authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
