package com.ssafy.togeball.domain.notice.repository;

import com.ssafy.togeball.domain.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer> {

//    Optional<Notice> findByMatchingIdAndUserId(Long matchingId, Long userId);

    List<Notice> findByUserId(Integer userId);

//    List<Notice> findByMatchingId(Long matchingId);
}
