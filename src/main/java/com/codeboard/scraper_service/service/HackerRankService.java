package com.codeboard.scraper_service.service;

import com.codeboard.scraper_service.dto.HackerRankStatsDto;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class HackerRankService {

    public HackerRankStatsDto getStats(String username) {
        HackerRankStatsDto dto = new HackerRankStatsDto();
        dto.setUsername(username);

        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
            headers.set("Accept", "application/json");
            headers.set("Referer", "https://www.hackerrank.com/" + username);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            String url = "https://www.hackerrank.com/rest/hackers/" + username + "/badges";
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

            List<Map> badges = (List<Map>) response.getBody().get("models");
            List<String> badgeNames = new ArrayList<>();
            int totalStars = 0;

//            if (badges != null) {
//                for (Map badge : badges) {
//                    // UPDATED: Using "badge_name" instead of "name"
//                    String badgeName = (String) badge.get("badge_name");
//                    if (badgeName != null) {
//                        badgeNames.add(badgeName);
//                    }
//
//                    Object stars = badge.get("stars");
//                    if (stars != null) totalStars += ((Number) stars).intValue();
//                }
//            }
            if (badges != null) {
                for (Map badge : badges) {
                    String badgeName = (String) badge.get("badge_name");
                    Object starsObj = badge.get("stars");

                    int stars = 0;
                    if (starsObj != null) {
                        stars = ((Number) starsObj).intValue();
                        totalStars += stars; // Add to the total count
                    }

                    // ONLY add the badge to the list if you have actually earned a star in it
                    if (badgeName != null && stars > 0) {
                        badgeNames.add(badgeName);
                    }
                }
            }

            dto.setBadges(badgeNames);
            dto.setTotalStars(totalStars + " stars");

        } catch (Exception e) {
            System.out.println("HackerRank error: " + e.getMessage());
            dto.setBadges(new ArrayList<>());
            dto.setTotalStars("0 stars");
        }

        return dto;
    }
}