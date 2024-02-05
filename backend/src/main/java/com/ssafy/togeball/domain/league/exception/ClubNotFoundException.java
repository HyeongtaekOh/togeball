package com.ssafy.togeball.domain.league.exception;

import com.ssafy.togeball.domain.common.exception.ApiException;

public class ClubNotFoundException extends ApiException {
    public ClubNotFoundException() {
        super(LeagueErrorCode.CLUB_NOT_FOUND);
    }
}