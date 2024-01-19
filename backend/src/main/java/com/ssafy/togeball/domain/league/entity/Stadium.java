package com.ssafy.togeball.domain.league.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stadium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="stadium_id")
    private byte id;

    @ManyToOne //클럽 하나가 여러 구장을 가질 수 있다
    @JoinColumn(name = "club_id")
    private Club club;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private double latitude;

    @Column
    private double longitude;

    @Builder
    public Stadium(Club club, String name, double latitude, double longitude) {
        this.club = club;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
