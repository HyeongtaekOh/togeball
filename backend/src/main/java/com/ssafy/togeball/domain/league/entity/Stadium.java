package com.ssafy.togeball.domain.league.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "TBL_STADIUM")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stadium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="stadium_id")
    private Integer id;

    @OneToMany
    private List<Club> clubs;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "full_name")
    private String fullName;

    @Column
    private String address;

    @OneToMany(mappedBy = "stadium")
    private List<ClubStadium> clubStadiums = new ArrayList<>();

    @OneToMany(mappedBy = "stadium", fetch = FetchType.LAZY)
    private List<Game> games;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    public Stadium(List<Club> clubs, String name) {
        this.clubs = clubs;
        this.name = name;
    }
    @Builder
    public Stadium(List<Club> clubs, String name, String fullName, String address, double latitude, double longitude) {
        this.clubs = clubs;
        this.name = name;
        this.fullName = fullName;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
