package com.ssafy.togeball.domain.league.service;

import com.ssafy.togeball.domain.league.dto.ClubResponse;
import com.ssafy.togeball.domain.league.dto.GameResponse;
import com.ssafy.togeball.domain.league.entity.Club;
import com.ssafy.togeball.domain.league.entity.Game;
import com.ssafy.togeball.domain.league.repository.ClubRepository;
import com.ssafy.togeball.domain.league.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeagueService {

    private final ClubRepository clubRepository;
    private final GameRepository gameRepository;

    @Transactional(readOnly = true)
    public List<GameResponse> findByClubId(Integer clubId) {
        List<Game> games = gameRepository.findByClubId(clubId);
        return games.stream()
                .map(GameResponse::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<GameResponse> findByDate(Date startDate, Date endDate) {
        List<Game> games = gameRepository.findByDate(startDate, endDate);
        return games.stream()
                .map(GameResponse::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<GameResponse> findBySponsorNameAndDate(Date date, String sponsorName) {

        List<Game> games = gameRepository.findBySponsorNameAndDate(date, sponsorName);
        return games.stream()
                .map(GameResponse::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ClubResponse> sortByRanking() {
        List<Club> clubs = clubRepository.findAllByOrderByRankingAsc();
        return clubs.stream()
                .map(ClubResponse::of)
                .toList();
    }
}
