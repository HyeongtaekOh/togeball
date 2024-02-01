package com.ssafy.togeballmatching;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {

    private final RedisTemplate<String, Object> redisTemplate;

    @GetMapping
    public String query(String key) {
        List<Object> list = redisTemplate.opsForList().range(key, 0, -1);
        assert list != null;
        list.forEach(o -> log.info("createdAt: {}", ((TestEntity) o).getCreatedAt()));
        return list.toString();
    }

    @PostMapping
    public String save(String key) {
        TestEntity testEntity = TestEntity.builder()
                .name("홍길동")
                .age(20)
                .height(180.5)
                .createdAt(Instant.now())
                .build();
        log.info("createdAt: {}", testEntity.getCreatedAt());
        redisTemplate.opsForList().leftPush(key, testEntity);
        return "success";
    }
}
