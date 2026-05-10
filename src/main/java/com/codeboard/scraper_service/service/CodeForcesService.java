package com.codeboard.scraper_service.service;

import com.codeboard.scraper_service.dto.CodeForcesStatsDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class CodeForcesService {

    private static final String CF_USER_API = "https://codeforces.com/api/user.info?handles=";
    private static final String CF_STATUS_API = "https://codeforces.com/api/user.status?handle=";

    public CodeForcesStatsDto getStats(String username) {
        RestTemplate restTemplate = new RestTemplate();
        CodeForcesStatsDto dto = new CodeForcesStatsDto();
        dto.setUsername(username);

        try {
            // Get user info
            Map userResponse = restTemplate.getForObject(CF_USER_API + username, Map.class);
            List<Map> results = (List<Map>) userResponse.get("result");
            Map user = results.get(0);

            dto.setRating(user.get("rating") != null ? ((Number) user.get("rating")).intValue() : 0);
            dto.setMaxRating(user.get("maxRating") != null ? ((Number) user.get("maxRating")).intValue() : 0);
            dto.setRank(user.get("rank") != null ? (String) user.get("rank") : "unrated");
            dto.setMaxRank(user.get("maxRank") != null ? (String) user.get("maxRank") : "unrated");
            dto.setContribution(user.get("contribution") != null ? ((Number) user.get("contribution")).intValue() : 0);

            // Count solved problems
            Map statusResponse = restTemplate.getForObject(CF_STATUS_API + username + "&from=1&count=10000", Map.class);
            List<Map> submissions = (List<Map>) statusResponse.get("result");

            Set<String> solved = new HashSet<>();
            for (Map submission : submissions) {
                if ("OK".equals(submission.get("verdict"))) {
                    Map problem = (Map) submission.get("problem");
                    solved.add(problem.get("contestId") + "-" + problem.get("index"));
                }
            }
            dto.setProblemsSolved(solved.size());

        } catch (Exception e) {
            System.out.println("Error fetching Codeforces stats: " + e.getMessage());
        }

        return dto;
    }
}