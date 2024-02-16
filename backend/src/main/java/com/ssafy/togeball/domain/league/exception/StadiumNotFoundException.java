package com.ssafy.togeball.domain.league.exception;

import com.ssafy.togeball.domain.common.exception.ApiException;

public class StadiumNotFoundException extends ApiException {
    public StadiumNotFoundException() {
        super(LeagueErrorCode.STADIUM_NOT_FOUND);
    }
}
