package com.ssafy.togeball.domain.league.exception;

import com.ssafy.togeball.domain.common.exception.ApiException;

public class GameNotFoundException extends ApiException {
    public GameNotFoundException() {
        super(LeagueErrorCode.GAME_NOT_FOUND);
    }
}