package com.ssafy.togeball.domain.league.exception;

import com.ssafy.togeball.domain.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum LeagueErrorCode implements ErrorCode {

    CLUB_NOT_FOUND(HttpStatus.NOT_FOUND, "club not found"),
    GAME_NOT_FOUND(HttpStatus.NOT_FOUND, "game not found"),
    STADIUM_NOT_FOUND(HttpStatus.NOT_FOUND, "stadium not found"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
