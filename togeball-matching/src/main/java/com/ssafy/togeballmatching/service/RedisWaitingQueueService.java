package com.ssafy.togeballmatching.service;

import com.ssafy.togeballmatching.dto.MatchingUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
@RequiredArgsConstructor
public class RedisWaitingQueueService implements WaitingQueueService {

    private final RedisTemplate<String, MatchingUser> redisTemplate;

    @Override
    public void addQueue(MatchingUser matchingUser) {
        redisTemplate.opsForList().rightPush("waiting-queue", matchingUser);
    }

    @Override
    public List<MatchingUser> getWaitingUsers() {
        return redisTemplate.opsForList().range("waiting-queue", 0, -1);
    }

    @Override
    public List<MatchingUser> getFirstNWaitingUsers(int n) {
        return redisTemplate.opsForList().range("waiting-queue", 0, n - 1);
    }

    @Override
    public void removeFirstNQueue(int n) {
        redisTemplate.opsForList().trim("waiting-queue", n, -1);
    }

    @Override
    public void clearQueue() {
        redisTemplate.delete("waiting-queue");
    }
}
