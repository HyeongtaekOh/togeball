package com.ssafy.togeballmatching.service.queue;

import com.ssafy.togeballmatching.dto.MatchingUser;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
//@Primary
public class MemoryWaitingQueueService implements WaitingQueueService {

    private final Map<Integer, MatchingUser> waitingQueue = new ConcurrentHashMap<>();


    @Override
    public void addQueue(MatchingUser matchingUser) {
        waitingQueue.put(matchingUser.getUserId(), matchingUser);
    }

    @Override
    public void removeQueue(Integer userId) {
        waitingQueue.remove(userId);
    }

    @Override
    public List<MatchingUser> getWaitingUsers() {
        return waitingQueue.values().stream().toList();
    }

    @Override
    public void clearQueue() {
        waitingQueue.clear();
    }
}
