package com.ssafy.togeball.domain.league.entity;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StadiumTest {

    @Test
    void StadiumBuilderTest() {
        //Given
        List<Club> club = new ArrayList<>();
        Club club1 = new Club("LG","트윈스","Twins",(byte)1);
        Club club2 = new Club("두산","베어스","Bears",(byte)5);
        club.add(club1);
        club.add(club2);
        String name = "잠실";
        String fullName = "잠실종합운동장";
        String address = "서울특별시 송파구 올림픽로 25 서울종합운동장";
        double latitude = 37.51480219999801;
        double longitude = 127.07362609999903;

        //When
        Stadium stadium = Stadium.builder()
                .club(club)
                .name(name)
                .fullName(fullName)
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .build();

        //Then
        System.out.println(stadium.toString());
    }
}
