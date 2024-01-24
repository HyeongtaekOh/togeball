package com.ssafy.togeball.domain.league.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "TBL_CLUB_STADIUM", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"club_id", "stadium_id"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubStadium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_stadium_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_id")
    private Stadium stadium;

    @Builder
    public ClubStadium(Club club, Stadium stadium) {
        this.club = club;
        this.stadium = stadium;
    }
}
