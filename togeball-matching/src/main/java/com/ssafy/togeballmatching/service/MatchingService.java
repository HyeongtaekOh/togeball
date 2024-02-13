package com.ssafy.togeballmatching.service;

import com.ssafy.togeballmatching.dto.MatchingRequest;
import com.ssafy.togeballmatching.dto.Tag;
import com.ssafy.togeballmatching.dto.TagType;
import com.ssafy.togeballmatching.dto.MatchingUser;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MatchingService {

    private final int MIN_GROUP_SIZE = 2;
    private final int MAX_GROUP_SIZE = 6;

    public List<MatchingRequest> matchUsers(List<MatchingUser> users) {

        List<MatchingRequest> matchings = new ArrayList<>();

        List<List<MatchingUser>> matchedUsersList = groupUsers(users);

        for (List<MatchingUser> matchedUsers : matchedUsersList) {
            matchings.add(makeMatchingRequest(matchedUsers));
        }

        return matchings;
    }

    private List<List<MatchingUser>> groupUsers(List<MatchingUser> users) {

        // 사용자들을 선호 경기장 기준으로 그룹화
        Map<String, List<MatchingUser>> stadiumGroups = new HashMap<>();
        users.forEach(user -> user.getTags().stream()
                .filter(tag -> tag.getType().equals(TagType.PREFERRED_STADIUM))
                .forEach(tag -> {
                    String preferredStadium = tag.getContent();
                    stadiumGroups.computeIfAbsent(preferredStadium, k -> new ArrayList<>()).add(user);
                }));

        List<List<MatchingUser>> finalGroups = new ArrayList<>();
        Set<MatchingUser> allocatedUsers = new HashSet<>();
        for (Map.Entry<String, List<MatchingUser>> entry : stadiumGroups.entrySet()) {
            List<MatchingUser> group = entry.getValue().stream().filter(user ->
                    !allocatedUsers.contains(user)).collect(Collectors.toList());

            if (group.size() >= MIN_GROUP_SIZE && group.size() <= MAX_GROUP_SIZE) {
                finalGroups.add(group);
                allocatedUsers.addAll(group);
            }
            else if (group.size() > MAX_GROUP_SIZE) {
                finalGroups.addAll(divideGroups(group));
                allocatedUsers.addAll(group);
            }
        }

        // 남은 사용자들을 그룹화
        ensureAllUsersMatched(users, finalGroups, allocatedUsers);

        return finalGroups;
    }

    private double calculateSimilarity(MatchingUser user1, MatchingUser user2) {

        Set<String> tags1 = user1.getTags().stream().map(Tag::getContent).collect(Collectors.toSet());
        Set<String> tags2 = user2.getTags().stream().map(Tag::getContent).collect(Collectors.toSet());
        long commonElements = tags1.stream().filter(tags2::contains).count();
        return commonElements / (double) (tags1.size() + tags2.size() - commonElements); // Jaccard 유사도
    }

    private List<List<MatchingUser>> divideGroups(List<MatchingUser> users) {

        // 사용자 간 유사도 matrix
        double[][] similarityMatrix = new double[users.size()][users.size()];
        for (int i = 0; i < users.size(); i++) {
            for (int j = 0; j < users.size(); j++) {
                if (i == j) continue;
                similarityMatrix[i][j] = calculateSimilarity(users.get(i), users.get(j));
            }
        }

        // 사용자를 개별 그룹으로 초기화
        List<Set<Integer>> groups = IntStream.range(0, users.size())
                .mapToObj(i -> new HashSet<>(List.of(i)))
                .collect(Collectors.toList());

        // 유사도가 가장 높은 그룹을 병합
        while (groups.size() > 1) {
            double maxSimilarity = -1;
            int mergeIndexA = -1, mergeIndexB = -1;
            for (int i = 0; i < groups.size(); i++) {
                for (int j = i + 1; j < groups.size(); j++) {
                    final int fj = j;
                    // 유사도 평균 계산
                    double similarity = groups.get(i).stream()
                            .flatMapToDouble(x -> groups.get(fj).stream().mapToDouble(y -> similarityMatrix[x][y]))
                            .average().orElse(0);
                    // 더 유사도가 높은 그룹쌍이면 mergeIndex 갱신
                    if (similarity > maxSimilarity) {
                        maxSimilarity = similarity;
                        mergeIndexA = i;
                        mergeIndexB = fj;
                    }
                }
            }
            if (mergeIndexA != -1 && mergeIndexB != -1) {
                groups.get(mergeIndexA).addAll(groups.get(mergeIndexB));
                groups.remove(mergeIndexB);
            }
        }

        List<List<MatchingUser>> preDividedGroups = groups.stream()
                .map(group -> group.stream().map(users::get).collect(Collectors.toList()))
                .toList();

        List<List<MatchingUser>> dividedGroups = new ArrayList<>();
        for (List<MatchingUser> group : preDividedGroups) {
            if (group.size() <= MAX_GROUP_SIZE) {
                dividedGroups.add(group);
                continue;
            }
            dividedGroups.addAll(furtherDivideGroup(group));
        }

        return dividedGroups;
    }

    // 유사도 그룹이 최대 크기를 초과할 때
    private List<List<MatchingUser>> furtherDivideGroup(List<MatchingUser> group) {

        List<List<MatchingUser>> groups = new ArrayList<>();
        int start = 0;
        while (start < group.size()) {
            int end = Math.min(start + MAX_GROUP_SIZE, group.size());
            groups.add(new ArrayList<>(group.subList(start, end)));
            start = end;
        }
        return groups;
    }

    private void ensureAllUsersMatched(List<MatchingUser> users, List<List<MatchingUser>> finalGroups, Set<MatchingUser> allocatedUsers) {

        List<MatchingUser> unmatchedUsers = users.stream()
                .filter(user -> !allocatedUsers.contains(user))
                .collect(Collectors.toList());

        if (unmatchedUsers.size() < MIN_GROUP_SIZE) { // 한 사람만 남았다면 마지막 조에 편입
            finalGroups.get(finalGroups.size() - 1).addAll(unmatchedUsers);
        } else if (unmatchedUsers.size() <= MAX_GROUP_SIZE) { // 적정 그룹 크기라면 새로운 그룹으로 편입
            finalGroups.add(unmatchedUsers);
        } else { // 최대 그룹 크기를 초과하면 나누어 편입
            finalGroups.addAll(divideGroups(unmatchedUsers));
        }
    }

    private MatchingRequest makeMatchingRequest(List<MatchingUser> users) {

        List<Integer> userIds = users.stream()
                .map(MatchingUser::getUserId)
                .toList();

        Set<Integer> tagIds = users.stream()
                .flatMap(user -> user.getTags().stream())
                .map(Tag::getId)
                .collect(Collectors.toSet());

        return MatchingRequest.builder()
                .title("Matching Group On " + LocalDateTime.now())
                .capacity(userIds.size())
                .userIds(userIds)
                .tagIds(tagIds)
                .build();
    }
}
