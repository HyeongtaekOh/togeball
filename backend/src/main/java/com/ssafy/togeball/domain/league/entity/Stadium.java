package com.ssafy.togeball.domain.league.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @ToString
@Table(name = "TBL_STADIUM")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stadium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="stadium_id")
    private byte id;

    @OneToMany
    private List<Club> club;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "full_name")
    private String fullName;

    @Column
    private String address;

    @Column
    private double latitude;

    @Column
    private double longitude;

    public Stadium(List<Club> club, String name) {
        this.club = club;
        this.name = name;
    }
    @Builder
    public Stadium(List<Club> club, String name, String fullName, String address, double latitude, double longitude) {
        this.club = club;
        this.name = name;
        this.fullName = fullName;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
