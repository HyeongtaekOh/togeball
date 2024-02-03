package com.ssafy.togeballmatching.service.queue;

import com.ssafy.togeballmatching.dto.MatchingUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemoryWaitingQueueService implements WaitingQueueService {

    private final List<MatchingUser> waitingQueue = new ArrayList<>();


    @Override
    public void addQueue(MatchingUser matchingUser) {
        waitingQueue.add(matchingUser);
    }

    @Override
    public List<MatchingUser> getWaitingUsers() {
        return waitingQueue;
    }

    @Override
    public List<MatchingUser> getFirstNWaitingUsers(int n) {
        return waitingQueue.subList(0, n);
    }

    @Override
    public void removeFirstNQueue(int n) {
        waitingQueue.subList(0, n).clear();
    }

    @Override
    public void clearQueue() {
        waitingQueue.clear();
    }
}
