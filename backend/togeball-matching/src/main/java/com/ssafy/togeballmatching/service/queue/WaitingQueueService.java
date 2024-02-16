package com.ssafy.togeballmatching.service.queue;

import com.ssafy.togeballmatching.dto.MatchingUser;

import java.util.List;

public interface WaitingQueueService {

    public void addQueue(MatchingUser matchingUser);

    public void removeQueue(Integer userId);

    public List<MatchingUser> getWaitingUsers();

    public void clearQueue();
}
