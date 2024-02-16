package com.ssafy.togeball.domain.auth.aop;

import com.ssafy.togeball.domain.auth.exception.AuthNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class UserContextAspect {

    @Around("@annotation(UserContext)")
    public Object userContext(ProceedingJoinPoint joinPoint) throws Throwable {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            log.error("UserContextAspect authentication is null");
            throw new AuthNotFoundException();
        }
        User principal = (User) authentication.getPrincipal();
        Integer userId = Integer.valueOf(principal.getUsername());
        log.info("UserContextAspect userId: {}", userId);

        Object[] args = joinPoint.getArgs();
        args[0] = userId;

        return joinPoint.proceed(args);
    }
}
