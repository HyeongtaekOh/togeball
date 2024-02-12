package com.ssafy.togeballchatting.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AuthenticationFailedException extends RuntimeException {
    public AuthenticationFailedException(String message) {
        super(message);
    }
}
