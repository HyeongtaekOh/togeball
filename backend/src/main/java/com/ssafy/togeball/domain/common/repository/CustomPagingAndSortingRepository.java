package com.ssafy.togeball.domain.common.repository;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class CustomPagingAndSortingRepository {

    public <T> Page<T> fetchPage(JPAQuery<T> query, Pageable pageable) {
        List<T> content = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = query.fetch().size();
        return new PageImpl<>(content, pageable, total);
    }
}
