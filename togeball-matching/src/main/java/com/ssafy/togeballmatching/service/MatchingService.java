package com.ssafy.togeballmatching.service;

import com.ssafy.togeballmatching.dto.MatchingRequest;
import com.ssafy.togeballmatching.dto.Tag;
import com.ssafy.togeballmatching.dto.TagType;
import com.ssafy.togeballmatching.dto.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class MatchingService {

    private final int MIN_GROUP_SIZE = 2;
    private final int MAX_GROUP_SIZE = 6;

    public List<MatchingRequest> matchUsers(List<Integer> waitingUserIds) {
        List<MatchingRequest> matchings = new ArrayList<>();

        List<User> users = fetchUsersByIds(waitingUserIds);

        List<List<User>> matchedUsersList = groupUsers(users);

        for (List<User> matchedUsers : matchedUsersList) {
            matchings.add(makeMatchingRequest(matchedUsers));
        }

        return matchings;
    }

    private List<List<User>> groupUsers(List<User> users) {
        Map<String, List<User>> stadiumGroups = new HashMap<>();
        users.forEach(user -> user.getTags().stream()
                .filter(tag -> tag.getType().equals(TagType.PREFERRED_STADIUM))
                .forEach(tag -> {
                    String preferredStadium = tag.getContent();
                    stadiumGroups.computeIfAbsent(preferredStadium, k -> new ArrayList<>()).add(user);
                }));

        List<List<User>> finalGroups = new ArrayList<>();
        Set<User> allocatedUsers = new HashSet<>();
        for (Map.Entry<String, List<User>> entry : stadiumGroups.entrySet()) {
            List<User> group = entry.getValue().stream().filter(user ->
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

        ensureAllUsersMatched(users, finalGroups, allocatedUsers);

        return finalGroups;
    }

    private double calculateSimilarity(User user1, User user2) {
        Set<String> tags1 = user1.getTags().stream().map(Tag::getContent).collect(Collectors.toSet());
        Set<String> tags2 = user2.getTags().stream().map(Tag::getContent).collect(Collectors.toSet());
        long commonElements = tags1.stream().filter(tags2::contains).count();
        return commonElements / (double) (tags1.size() + tags2.size() - commonElements); // Jaccard 유사도
    }

    private List<List<User>> divideGroups(List<User> users) {
        // 사용자 간 유사도 matrix
        double[][] similarityMatrix = new double[users.size()][users.size()];
        for (int i = 0; i < users.size(); i++) {
            for (int j = 0; j < users.size(); j++) {
                similarityMatrix[i][j] = (i == j) ? 0 : calculateSimilarity(users.get(i), users.get(j));
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
                    final int fj = j; // effectively final for use in lambda
                    double similarity = groups.get(i).stream()
                            .flatMapToInt(x -> groups.get(fj).stream().mapToInt(y -> (int) similarityMatrix[x][y]))
                            .average().orElse(0);
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

        List<List<User>> preDividedGroups = groups.stream()
                .map(group -> group.stream().map(users::get).collect(Collectors.toList()))
                .toList();

        List<List<User>> dividedGroups = new ArrayList<>();
        for (List<User> group : preDividedGroups) {
            dividedGroups.addAll(furtherDivideGroup(group));
        }

        return dividedGroups;
    }

    // 유사도 그룹이 최대 크기를 초과할 때
    private List<List<User>> furtherDivideGroup(List<User> group) {
        List<List<User>> dividedGroups = new ArrayList<>();
        int start = 0;
        while (start < group.size()) {
            int end = Math.min(start + MAX_GROUP_SIZE, group.size());
            dividedGroups.add(new ArrayList<>(group.subList(start, end)));
            start = end;
        }
        return dividedGroups;
    }

    private void ensureAllUsersMatched(List<User> users, List<List<User>> finalGroups, Set<User> allocatedUsers) {
        List<User> unmatchedUsers = users.stream()
                .filter(user -> !allocatedUsers.contains(user))
                .collect(Collectors.toList());

        // 한 사람만 남았다면 마지막 조에 편입
        if (unmatchedUsers.size() < MIN_GROUP_SIZE) {
            finalGroups.get(finalGroups.size() - 1).addAll(unmatchedUsers);
        } else if (unmatchedUsers.size() <= MAX_GROUP_SIZE) {
            finalGroups.add(unmatchedUsers);
        } else {
            finalGroups.addAll(divideGroups(unmatchedUsers));
        }
    }

    public MatchingRequest makeMatchingRequest(List<User> users) {

        Set<Integer> userIds = users.stream()
                .map(User::getId)
                .collect(Collectors.toSet());

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

    public List<User> fetchUsersByIds(List<Integer> userIds) {
        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "https://i10a610.p.ssafy.io:8080/api/users/userIds";

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);
        for (Integer userId : userIds) {
            uriBuilder.queryParam("userId", userId);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        return responseEntity.getBody();
    }

    public static void main(String[] args) {
        MatchingService matchingService = new MatchingService();

        List<Integer> userIds = new ArrayList<>();
        for (int i = 1; i < 12; ++i) {
            userIds.add(i);
        }

        try {
            List<User> users = matchingService.fetchUsersByIds(userIds);
            List<List<User>> groups = matchingService.groupUsers(users);
            for (List<User> group : groups) {
                System.out.println(group);
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}