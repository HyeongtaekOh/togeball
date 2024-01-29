package com.ssafy.togeball.domain.league.service;

import com.ssafy.togeball.domain.league.dto.ClubResponse;
import com.ssafy.togeball.domain.league.dto.GameResponse;
import com.ssafy.togeball.domain.league.entity.Game;

import java.util.Date;
import java.util.List;

public interface LeagueService {
    
    List<GameResponse> findBySponsorName(String sponsorName);

    List<GameResponse> findByDate(Date startDate, Date endDate);

    List<GameResponse> findBySponsorNameAndDate(Date date, String sponsorName);
    
    List<ClubResponse> sortByRanking();

    List<GameResponse> convertToGameResponseList(List<Game> games);

}
