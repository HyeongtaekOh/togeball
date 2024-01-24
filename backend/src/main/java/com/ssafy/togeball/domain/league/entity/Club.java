package com.ssafy.togeball.domain.league.entity;

import com.ssafy.togeball.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "TBL_CLUB")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="club_id")
    private Integer id;

    @Column(name = "sponsor_name", nullable = false, unique = true)
    private String sponsorName;

    @Column(name = "club_name", nullable = false, unique = true)
    private String clubName;

    @Column
    private String logo;

    @Column
    private Integer ranking;

    @OneToMany(mappedBy = "club")
    private List<ClubStadium> clubStadiums = new ArrayList<>();

    @OneToMany(mappedBy = "homeClub", fetch = FetchType.LAZY)
    private List<Game> homeGames;

    @OneToMany(mappedBy = "awayClub", fetch = FetchType.LAZY)
    private List<Game> awayGames;

    @OneToMany(mappedBy = "club")
    private List<User> fans;

    @Builder
    public Club(String sponsorName, String clubName, String logo, Integer ranking) {
        this.sponsorName = sponsorName;
        this.clubName = clubName;
        this.logo = logo;
        this.ranking = ranking;
    }
}
