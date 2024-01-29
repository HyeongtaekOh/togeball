package com.ssafy.togeball.domain.league.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "TBL_GAME")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY) //구장 하나가 여러 경기를 가질 수 있다
    @JoinColumn(name = "stadium_id", nullable = false)
    private Stadium stadium;

    @ManyToOne(fetch = FetchType.LAZY) //구단 하나가 여러 경기를 가질 수 있다
    @JoinColumn(name = "home_club_id", nullable = false)
    private Club homeClub;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_club_id", nullable = false)
    private Club awayClub;

    @Column(nullable = false)
    private LocalDateTime datetime;

    @Builder
    public Game(Stadium stadium, Club homeClub, Club awayClub, LocalDateTime datetime) {
        this.stadium = stadium;
        this.homeClub = homeClub;
        this.awayClub = awayClub;
        this.datetime = datetime;
    }
}
