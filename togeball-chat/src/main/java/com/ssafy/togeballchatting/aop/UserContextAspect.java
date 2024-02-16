package com.ssafy.togeballchatting.aop;

import com.ssafy.togeballchatting.exception.AuthenticationFailedException;
import com.ssafy.togeballchatting.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class UserContextAspect {

    private final JwtService jwtService;

    @Around("@annotation(UserContext)")
    public Object userContext(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = getRequest(joinPoint.getArgs());

        if (request == null) {
            log.error("UserContextAspect request is null");
            throw new AuthenticationFailedException("UserContextAspect request is null");
        }

        Integer userId = jwtService.extractAccessToken(request)
                .filter(jwtService::isTokenValid)
                .flatMap(jwtService::extractId)
                .orElse(null);

        if (userId == null) {
            log.error("UserContextAspect userId is null");
            throw new AuthenticationFailedException("UserContextAspect userId is null");
        }
        log.info("UserContextAspect userId: {}", userId);

        Object[] args = joinPoint.getArgs();
        args[0] = userId;

        return joinPoint.proceed(args);
    }

    private HttpServletRequest getRequest(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof HttpServletRequest) {
                return (HttpServletRequest) arg;
            }
        }
        return null;
    }
}
