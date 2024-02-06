package com.ssafy.togeball.domain.notice.repository;

import com.ssafy.togeball.domain.matching.entity.Matching;
import com.ssafy.togeball.domain.user.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class NoticeRepositoryTest {

    @Autowired
    NoticeRepository noticeRepository;

    private User user1;

    private User user2;

    private Matching matching1;

    private Matching matching2;

    void dataInit() {

    }

    @Test
    void noticeSaveTest() {

    }

    @Test
    void noticeDuplicateMatchingTest() {

    }

    @Test
    void noticeFindAllByUserIdTest() {

    }

}
