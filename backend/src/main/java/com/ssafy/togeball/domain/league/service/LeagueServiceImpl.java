package com.ssafy.togeball.domain.league.service;

import com.ssafy.togeball.domain.league.dto.ClubResponse;
import com.ssafy.togeball.domain.league.dto.GameResponse;
import com.ssafy.togeball.domain.league.entity.Club;
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
public class LeagueServiceImpl implements LeagueService {

    private final ClubRepository clubRepository;
    private final StadiumRepository stadiumRepository;
    private final ClubStadiumRepository clubStadiumRepository;
    private final GameRepository gameRepository;


//    @Override
//    public List<Integer> findAll() {
//        return gameRepository.findAll().stream().map(g -> g.getId()).toList();
//    }

    public List<GameResponse> findBySponsorName(String sponsorName) {
        List<Game> games = gameRepository.findBySponsorName(sponsorName);
        return convertToGameResponseList(games);
    }

    public List<GameResponse> findByDate(Date startDate, Date endDate) {
        List<Game> games = gameRepository.findByDate(startDate, endDate);
        return convertToGameResponseList(games);
    }

    public List<GameResponse> findBySponsorNameAndDate(Date date, String sponsorName) {
        List<Game> games = gameRepository.findBySponsorNameAndDate(date, sponsorName);
        return convertToGameResponseList(games);
    }

    public List<ClubResponse> sortByRanking() {
        List<Club> clubs = clubRepository.findAllByOrderByRankingAsc();
        return convertToClubResponseList(clubs);
    }

    public List<GameResponse> convertToGameResponseList(List<Game> games) {
        List<GameResponse> gameResponses = new ArrayList<>();
        for (int i = 0; i < games.size(); i++) {
            Game game = games.get(i);
            GameResponse gameResponse = GameResponse.of(game);
            gameResponses.add(gameResponse);
        }
        return gameResponses;
    }

    public List<ClubResponse> convertToClubResponseList(List<Club> clubs) {
        List<ClubResponse> clubResponses = new ArrayList<>();
        for (int i = 0; i < clubs.size(); i++) {
            Club club = clubs.get(i);
            ClubResponse clubResponse = ClubResponse.of(club);
            clubResponses.add(clubResponse);
        }
        return clubResponses;
    }
}
