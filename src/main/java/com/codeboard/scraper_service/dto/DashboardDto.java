package com.codeboard.scraper_service.dto;

import lombok.Data;

@Data
public class DashboardDto {
    private LeetCodeStatsDto leetCode;
    private CodeForcesStatsDto codeForces;
    private HackerRankStatsDto hackerRank;
    private SkillRackStatsDto skillRack;
    private int totalProblemsSolved;
    private int placementReadinessScore;
}