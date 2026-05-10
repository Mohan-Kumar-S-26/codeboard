package com.codeboard.scraper_service.dto;

import lombok.Data;

@Data
public class CodeForcesStatsDto {
    private String username;
    private int rating;
    private int maxRating;
    private String rank;
    private String maxRank;
    private int contribution;
    private int problemsSolved;
}