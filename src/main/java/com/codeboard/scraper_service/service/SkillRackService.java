package com.codeboard.scraper_service.service;

import com.codeboard.scraper_service.dto.SkillRackStatsDto;
import org.springframework.stereotype.Service;

@Service
public class SkillRackService {

    public SkillRackStatsDto getStats(String username, int problemsSolved, String rank) {
        SkillRackStatsDto dto = new SkillRackStatsDto();
        dto.setUsername(username);
        dto.setProblemsSolved(problemsSolved);
        dto.setRank(rank);
        return dto;
    }
}