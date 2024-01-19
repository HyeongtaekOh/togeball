package com.ssafy.togeball.domain.league.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="club_id")
    private byte id;

    @Column(name = "sponsor_name", nullable = false, unique = true)
    private String sponsorName;

    @Column(name = "club_name", nullable = false, unique = true)
    private String clubName;

    @Column(nullable = false)
    private byte ranking;

    @Builder
    public Club(String sponsorName, String clubName, byte ranking) {
        this.sponsorName = sponsorName;
        this.clubName = clubName;
        this.ranking = ranking;
    }
}
