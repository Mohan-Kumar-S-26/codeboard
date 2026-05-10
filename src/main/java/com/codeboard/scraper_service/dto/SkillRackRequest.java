package com.codeboard.scraper_service.dto;

import lombok.Data;

@Data
public class SkillRackRequest {
    private String username;
    private int problemsSolved;
    private String rank;
    private int points;
}