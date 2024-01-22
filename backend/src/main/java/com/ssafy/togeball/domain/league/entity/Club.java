package com.ssafy.togeball.domain.league.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @ToString
@Table(name = "TBL_CLUB")
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

    @Column(name = "club_eng_name")
    private String clubEngName;

    @Column(nullable = false)
    private byte ranking;

    @Builder
    public Club(String sponsorName, String clubName, String clubEngName, byte ranking) {
        this.sponsorName = sponsorName;
        this.clubName = clubName;
        this.clubEngName = clubEngName;
        this.ranking = ranking;
    }
}
