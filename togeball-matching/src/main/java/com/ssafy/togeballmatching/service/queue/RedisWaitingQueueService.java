package com.ssafy.togeballmatching.service.queue;

import com.ssafy.togeballmatching.dto.MatchingUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class RedisWaitingQueueService implements WaitingQueueService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final String REDIS_QUEUE_KEY = "togeball:waiting-queue";
    private final HashOperations<String, String, MatchingUser> hashOperations;

    @Autowired
    public RedisWaitingQueueService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void addQueue(MatchingUser matchingUser) {
        hashOperations.put(REDIS_QUEUE_KEY, String.valueOf(matchingUser.getUserId()), matchingUser);
    }

    @Override
    public void removeQueue(Integer userId) {
        hashOperations.delete(REDIS_QUEUE_KEY, String.valueOf(userId));
    }

    @Override
    public List<MatchingUser> getWaitingUsers() {
        return hashOperations.values(REDIS_QUEUE_KEY);
    }

    @Override
    public void clearQueue() {
        redisTemplate.delete(REDIS_QUEUE_KEY);
    }
}
