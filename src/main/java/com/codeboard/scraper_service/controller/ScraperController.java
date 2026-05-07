package com.codeboard.scraper_service.controller;

import com.codeboard.scraper_service.dto.LeetCodeStatsDto;
import com.codeboard.scraper_service.service.LeetCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scraper")
@RequiredArgsConstructor
public class ScraperController {

    private final LeetCodeService leetCodeService;

    @GetMapping("/leetcode/{username}")
    public ResponseEntity<LeetCodeStatsDto> getLeetCodeStats(
            @PathVariable String username) {
        LeetCodeStatsDto stats = leetCodeService.getStats(username);
        return ResponseEntity.ok(stats);
    }
}