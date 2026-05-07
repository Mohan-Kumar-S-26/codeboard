package com.codeboard.scraper_service.dto;

import lombok.Data;

@Data
public class LeetCodeStatsDto {
    private String username;
    private int totalSolved;
    private int easySolved;
    private int mediumSolved;
    private int hardSolved;
    private int ranking;
    private int contributionPoints;
}