package com.ssafy.togeball.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.togeball.domain.auth.handler.LoginFailureHandler;
import com.ssafy.togeball.domain.auth.handler.LoginSuccessHandler;
import com.ssafy.togeball.domain.auth.handler.OAuth2LoginFailureHandler;
import com.ssafy.togeball.domain.auth.handler.OAuth2LoginSuccessHandler;
import com.ssafy.togeball.domain.auth.service.AuthService;
import com.ssafy.togeball.domain.auth.service.CustomOAuth2UserService;
import com.ssafy.togeball.domain.security.filter.CustomAccessDeniedHandler;
import com.ssafy.togeball.domain.security.filter.CustomAuthenticationEntryPoint;
import com.ssafy.togeball.domain.security.filter.CustomJsonUsernamePasswordAuthenticationFilter;
import com.ssafy.togeball.domain.security.filter.JwtAuthenticationProcessingFilter;
import com.ssafy.togeball.domain.security.jwt.JwtService;
import com.ssafy.togeball.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final JwtService jwtService;
    private final AuthService authService;
    private final UserService userService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                /*
                 * 로그인 여부에 상관없이 접근이 가능하게 하기 위해선 다음과 같이 설정해주세요.
                 * .requestMatchers("/api/for-anyone").permitAll()
                 */
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/oauth2/**", "/h2-console/**", "/error", "/login/**", "/swagger-ui/**", "/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login", "/api/auth/reissue", "/api/auth/code").permitAll()
                        .requestMatchers("/api/users/me/**").authenticated()
                        .requestMatchers( "/api/users/**").permitAll()
                        .requestMatchers("/api/hashtags/**").permitAll() // 개발용
                        .requestMatchers("/api/league/**").permitAll()
                        .requestMatchers("/api/posts/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/chatrooms").permitAll()
                        .requestMatchers("/api/matching").hasRole("ADMIN")
                        .requestMatchers("/api/chatrooms/**").authenticated()
                        .anyRequest().authenticated())
                .oauth2Login(oauth2Login -> oauth2Login.permitAll()
                        .successHandler(oAuth2LoginSuccessHandler)
                        .failureHandler(oAuth2LoginFailureHandler)
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOAuth2UserService)
                        ))
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
                )
                .logout(AbstractHttpConfigurer::disable);

        http.addFilterAfter(customJsonUsernamePasswordAuthenticationFilter(), LogoutFilter.class);
        http.addFilterBefore(jwtAuthenticationProcessingFilter(), CustomJsonUsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(authService);
        return new ProviderManager(provider);
    }

    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler(jwtService, authService, userService);
    }

    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    @Bean
    public CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter() {
        CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordLoginFilter
                = new CustomJsonUsernamePasswordAuthenticationFilter(objectMapper, userService);
        customJsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
        customJsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
        customJsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return customJsonUsernamePasswordLoginFilter;
    }

    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter() {
        return new JwtAuthenticationProcessingFilter(jwtService, userService, authService);
    }
}
