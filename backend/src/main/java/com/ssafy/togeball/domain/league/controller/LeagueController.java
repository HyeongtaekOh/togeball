package com.ssafy.togeball.domain.league.controller;

import com.ssafy.togeball.domain.league.dto.ClubResponse;
import com.ssafy.togeball.domain.league.dto.GameResponse;
import com.ssafy.togeball.domain.league.service.LeagueService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/league")
public class LeagueController {

    private final LeagueService leagueService;

    //기간(시작일, 마지막일)으로 경기 조회
    @GetMapping("/games")
    public ResponseEntity<?> searchByDate(@RequestParam(name="startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                         @RequestParam(name="endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        List<GameResponse> games = leagueService.findByDate(startDate, endDate);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    //기업명(팀명)으로 경기 조회
    @GetMapping("/clubgames")
    public ResponseEntity<?> searchByClubId(@RequestParam(name="clubId") Integer clubId) {
        List<GameResponse> games = leagueService.findByClubId(clubId);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    //날짜와 팀명으로 경기 조회
    @GetMapping("/clubgame")
    public ResponseEntity<?> searchByDateAndSponsorName(@RequestParam(name="date") Date date,
            @RequestParam(name="sponsorName") String sponsorName) {
        List<GameResponse> games = leagueService.findBySponsorNameAndDate(date, sponsorName);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    //순위에 따라 팀 정렬하여 조회
    @GetMapping("/clubs")
    public ResponseEntity<?> sortByRanking() {
        List<ClubResponse> clubs = leagueService.sortByRanking();
        return new ResponseEntity<>(clubs, HttpStatus.OK);
    }
}
