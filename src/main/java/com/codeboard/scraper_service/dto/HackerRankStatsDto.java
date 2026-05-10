package com.codeboard.scraper_service.dto;

import lombok.Data;
import java.util.List;

@Data
public class HackerRankStatsDto {
    private String username;
    private List<String> badges;
    private String totalStars;
}