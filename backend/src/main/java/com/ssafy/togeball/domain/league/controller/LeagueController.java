package com.ssafy.togeball.domain.league.controller;

import com.ssafy.togeball.domain.league.dto.GameResponse;
import com.ssafy.togeball.domain.league.service.LeagueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/league")
public class LeagueController {

    private final LeagueService leagueService;

    @GetMapping("/games")
    public ResponseEntity<List<GameResponse>> findByDate(Date startDate, Date endDate) {
        List<GameResponse> games = leagueService.findByDate(startDate, endDate);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("/games/{sponsorName}")
    public ResponseEntity<List<GameResponse>> findBySponsorName(String sponsorName) {
        List<GameResponse> games = leagueService.findBySponsorName(sponsorName);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @GetMapping("/clubs")
    public ResponseEntity showClubRanking() {
        return null;
    }
}
