package com.ssafy.togeball.domain.matching.entity;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@Slf4j
@DataJpaTest
public class MatchingEntityTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void createAndCascadePersistTest() {

        Matching matching = Matching.builder()
                .title("title")
                .capacity(10)
                .build();


    }
}
