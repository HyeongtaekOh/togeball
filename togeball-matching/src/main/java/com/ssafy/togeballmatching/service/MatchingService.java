package com.ssafy.togeballmatching.service;

import com.ssafy.togeballmatching.dto.MatchingRequest;
import com.ssafy.togeballmatching.dto.Tag;
import com.ssafy.togeballmatching.dto.TagType;
import com.ssafy.togeballmatching.dto.MatchingUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
public class MatchingService {

    private final int MIN_GROUP_SIZE = 2;
    private final int MAX_GROUP_SIZE = 6;
    private final String ACCESS_TOKEN_HEADER = "authorization";
    private final String BEARER_PREFIX = "Bearer ";
    private final String OPENAI_PROMPT = "다음 태그를 가진 사용자가 참여하는 야구 직관 메이트 채팅방 제목을 생성해주세요: ";
    private final String GPT_MODEL = "gpt-3.5-turbo-instruct";
    private final String GPT_URL = "https://api.openai.com/v1/completions";

    @Value("${openai.api.key}")
    private String GPT_API_KEY;

    public List<MatchingRequest> matchUsers(List<MatchingUser> users) {

        log.info("matchUsers: {}", users);

        if (users.size() < MIN_GROUP_SIZE) {
            log.warn("매칭할 사용자가 부족합니다.");
            return Collections.emptyList();
        }

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
        int size;
         do {
            size = groups.size();
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
                // 두 그룹을 합쳐서 MAX_GROUP_SIZE를 초과하지 않도록
                if (groups.get(mergeIndexA).size() + groups.get(mergeIndexB).size() <= MAX_GROUP_SIZE) {
                    groups.get(mergeIndexA).addAll(groups.get(mergeIndexB));
                    groups.remove(mergeIndexB);
                }
            }
        } while (groups.size() != size);

        List<List<MatchingUser>> dividedGroups = groups.stream()
                .map(group -> group.stream().map(users::get).collect(Collectors.toList()))
                .toList();

        // dividedGroups를 돌면서 MIN_GROUP_SIZE보다 작은 그룹은 다른 그룹 중 가장 작은 그룹과 합침
        for (int i = 0; i < dividedGroups.size(); i++) {
            if (dividedGroups.get(i).size() < MIN_GROUP_SIZE) {
                int minSize = Integer.MAX_VALUE;
                int mergeIndex = -1;
                for (int j = 0; j < dividedGroups.size(); j++) {
                    if (i == j) continue;
                    if (dividedGroups.get(j).size() < minSize) {
                        minSize = dividedGroups.get(j).size();
                        mergeIndex = j;
                    }
                }
                if (mergeIndex != -1) {
                    dividedGroups.get(mergeIndex).addAll(dividedGroups.get(i));
                    dividedGroups.remove(i);
                    i--;
                }
            }
        }

        return dividedGroups;
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
                .title(getTitle(userIds))
                .capacity(userIds.size())
                .userIds(userIds)
                .tagIds(tagIds)
                .build();
    }

    private String getTitle(List<Integer> userIds) {
        List<Tag> tags = fetchTagsByUserIdsWithCount(userIds);

        if (tags.isEmpty()) {
            return "[error] 태그가 없는 채팅방 : " + LocalDateTime.now();
        }

        List<String> topTags = tags.stream()
                .limit(3)
                .map(Tag::getContent)
                .collect(Collectors.toList());

        String prompt = OPENAI_PROMPT + String.join(", ", topTags);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(ACCESS_TOKEN_HEADER, BEARER_PREFIX + GPT_API_KEY);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", GPT_MODEL);
        requestBody.put("prompt", prompt);
        requestBody.put("max_tokens", 60);
        requestBody.put("temperature", 0.7);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    GPT_URL,
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<>() {}
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                List<Map<String, String>> choices = (List<Map<String, String>>) response.getBody().get("choices");
                if (!choices.isEmpty()) {
                    String title = choices.get(0).get("text").trim();
                    return title.isEmpty() ? "[error] 제목 생성 실패 : " + LocalDateTime.now() : title;
                }
            }

            return String.join(", ", topTags) + "유저들의 채팅방";
        } catch (Exception e) {
            log.info("OpenAI API 호출 실패: {}", e.getMessage());
            return String.join(", ", topTags) + "유저들의 채팅방";
        }
    }

    public List<Tag> fetchTagsByUserIdsWithCount(List<Integer> userIds) {

        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "https://i10a610.p.ssafy.io:8080/api/hashtags/users/counts";

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseUrl);
        for (Integer userId : userIds) {
            uriBuilder.queryParam("userId", userId);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Tag>> responseEntity = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {}
        );

        return responseEntity.getBody();
    }
}
