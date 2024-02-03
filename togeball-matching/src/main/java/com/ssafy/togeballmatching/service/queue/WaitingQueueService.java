package com.ssafy.togeballmatching.service.queue;

import com.ssafy.togeballmatching.dto.MatchingUser;

import java.util.List;

public interface WaitingQueueService {

    public void addQueue(MatchingUser matchingUser);

    public List<MatchingUser> getWaitingUsers();

    public List<MatchingUser> getFirstNWaitingUsers(int n);

    public void removeFirstNQueue(int n);

    public void clearQueue();
}
