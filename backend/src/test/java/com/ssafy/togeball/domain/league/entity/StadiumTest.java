package com.ssafy.togeball.domain.league.entity;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class StadiumTest {

    @Test
    void StadiumBuilderTest() {

        //Given
        List<Club> clubs = new ArrayList<>();
        Club club1 = new Club("LG","트윈스","logo1.png", 1);
        Club club2 = new Club("두산","베어스","logo2.png", 5);
        clubs.add(club1);
        clubs.add(club2);
        String name = "잠실";
        String fullName = "잠실종합운동장";
        String address = "서울특별시 송파구 올림픽로 25 서울종합운동장";
        double latitude = 37.51480219999801;
        double longitude = 127.07362609999903;

        //When
        Stadium stadium = Stadium.builder()
                .clubs(clubs)
                .name(name)
                .fullName(fullName)
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .build();

        //Then
        System.out.println("Club (1개) : " + stadium.getClubs().get(0).getSponsorName());
        System.out.println("Name: " + stadium.getName());
        System.out.println("Full Name: " + stadium.getFullName());
        System.out.println("Address: " + stadium.getAddress());
        System.out.println("Latitude: " + stadium.getLatitude());
        System.out.println("Longitude: " + stadium.getLongitude());
    }
}
