package com.ssafy.togeball.domain.notice.repository;

import com.ssafy.togeball.domain.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    List<Notice> findAllByUserId(Integer userId);
}
