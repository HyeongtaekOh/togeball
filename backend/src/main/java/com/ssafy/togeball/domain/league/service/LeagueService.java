package com.ssafy.togeball.domain.league.service;

import com.ssafy.togeball.domain.league.dto.ClubResponse;
import com.ssafy.togeball.domain.league.dto.GameResponse;
import com.ssafy.togeball.domain.league.entity.Game;
import com.ssafy.togeball.domain.league.repository.ClubRepository;
import com.ssafy.togeball.domain.league.repository.ClubStadiumRepository;
import com.ssafy.togeball.domain.league.repository.GameRepository;
import com.ssafy.togeball.domain.league.repository.StadiumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeagueService {

    private final ClubRepository clubRepository;
    private final StadiumRepository stadiumRepository;
    private final ClubStadiumRepository clubStadiumRepository;
    private final GameRepository gameRepository;



    public List<GameResponse> findBySponsorName(String sponsorName) {
        List<Game> games = gameRepository.findBySponsorName(sponsorName);
        return convertToDtoList(games);
    }

    public List<GameResponse> findByDate(Date startDate, Date endDate) {
        List<Game> games = gameRepository.findByDate(startDate, endDate);
        return convertToDtoList(games);
    }

    public List<GameResponse> convertToDtoList(List<Game> games) {
        List<GameResponse> gameResponse = new ArrayList<>();
        //TODO: 채워넣기
        return gameResponse;
    }
}
