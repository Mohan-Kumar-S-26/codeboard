package com.codeboard.scraper_service.service;

import com.codeboard.scraper_service.dto.LeetCodeStatsDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.Map;

@Service
public class LeetCodeService {

    private static final String LEETCODE_API = "https://leetcode.com/graphql";

    public LeetCodeStatsDto getStats(String username) {

        String query = """
            {
              "query": "{ matchedUser(username: \\"%s\\") { username submitStats { acSubmissionNum { difficulty count } } profile { ranking } } }",
              "variables": {}
            }
            """.formatted(username);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Referer", "https://leetcode.com");

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(query, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                LEETCODE_API,
                HttpMethod.POST,
                request,
                Map.class
        );

        return parseResponse(response.getBody(), username);
    }

    @SuppressWarnings("unchecked")
    private LeetCodeStatsDto parseResponse(Map body, String username) {
        LeetCodeStatsDto dto = new LeetCodeStatsDto();
        dto.setUsername(username);

        try {
            Map data = (Map) body.get("data");
            Map matchedUser = (Map) data.get("matchedUser");
            Map profile = (Map) matchedUser.get("profile");
            Map submitStats = (Map) matchedUser.get("submitStats");
            java.util.List<Map> acList = (java.util.List<Map>) submitStats.get("acSubmissionNum");

            dto.setRanking(((Number) profile.get("ranking")).intValue());

            for (Map item : acList) {
                String difficulty = (String) item.get("difficulty");
                int count = ((Number) item.get("count")).intValue();
                switch (difficulty) {
                    case "All" -> dto.setTotalSolved(count);
                    case "Easy" -> dto.setEasySolved(count);
                    case "Medium" -> dto.setMediumSolved(count);
                    case "Hard" -> dto.setHardSolved(count);
                }
            }
        } catch (Exception e) {
            System.out.println("Error parsing LeetCode response: " + e.getMessage());
        }

        return dto;
    }
}