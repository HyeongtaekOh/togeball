package com.ssafy.togeball.domain.league.service;

import com.ssafy.togeball.domain.league.dto.GameResponse;
import com.ssafy.togeball.domain.league.entity.Game;

import java.util.Date;
import java.util.List;

public interface LeagueService {

    List<GameResponse> findBySponsorName(String sponsorName); //기업 이름으로 경기 찾기

    List<GameResponse> findByDate(Date startDate, Date endDate); //날짜로 경기 찾기

    List<GameResponse> convertToDtoList(List<Game> games);
}
