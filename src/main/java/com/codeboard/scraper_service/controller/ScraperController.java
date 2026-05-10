package com.codeboard.scraper_service.controller;

import com.codeboard.scraper_service.dto.*;
import com.codeboard.scraper_service.entity.PlatformStats;
import com.codeboard.scraper_service.entity.User;
import com.codeboard.scraper_service.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    private final CodeForcesService codeForcesService;

    @GetMapping("/codeforces/{username}")
    public ResponseEntity<CodeForcesStatsDto> getCodeForcesStats(
            @PathVariable String username) {
        return ResponseEntity.ok(codeForcesService.getStats(username));
    }

    private final HackerRankService hackerRankService;

    @GetMapping("/hackerrank/{username}")
    public ResponseEntity<HackerRankStatsDto> getHackerRankStats(
            @PathVariable String username) {
        return ResponseEntity.ok(hackerRankService.getStats(username));
    }
    private final SkillRackService skillRackService;

    @PostMapping("/skillrack")
    public ResponseEntity<SkillRackStatsDto> getSkillRackStats(
            @RequestBody SkillRackRequest request) {
        return ResponseEntity.ok(
                skillRackService.getStats(
                        request.getUsername(),
                        request.getProblemsSolved(),
                        request.getRank()
                )
        );
    }
    private final UserService userService;

    @PostMapping("/user/register")
    public ResponseEntity<User> registerUser(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String college) {
        return ResponseEntity.ok(userService.registerUser(name, email, college));
    }

    @PostMapping("/user/{userId}/sync")
    public ResponseEntity<String> syncStats(
            @PathVariable Long userId,
            @RequestParam(required = false) String leetcode,
            @RequestParam(required = false) String codeforces,
            @RequestParam(required = false) String hackerrank) {
        userService.saveAllStats(userId, leetcode, codeforces, hackerrank);
        return ResponseEntity.ok("Stats synced successfully!");
    }

    @GetMapping("/user/{userId}/stats")
    public ResponseEntity<List<PlatformStats>> getUserStats(
            @PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserStats(userId));
    }
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDto> getDashboard(
            @RequestParam(required = false) String leetcode,
            @RequestParam(required = false) String codeforces,
            @RequestParam(required = false) String hackerrank,
            @RequestParam(required = false, defaultValue = "0") int skillrackSolved,
            @RequestParam(required = false, defaultValue = "0") String skillrackRank) {

        DashboardDto dashboard = new DashboardDto();

        if (leetcode != null) dashboard.setLeetCode(leetCodeService.getStats(leetcode));
        if (codeforces != null) dashboard.setCodeForces(codeForcesService.getStats(codeforces));
        if (hackerrank != null) dashboard.setHackerRank(hackerRankService.getStats(hackerrank));

        // Calculate total problems
        int total = 0;
        if (dashboard.getLeetCode() != null) total += dashboard.getLeetCode().getTotalSolved();
        if (dashboard.getCodeForces() != null) total += dashboard.getCodeForces().getProblemsSolved();
        if (skillrackSolved > 0) total += skillrackSolved;
        dashboard.setTotalProblemsSolved(total);

        // Simple placement score
        int score = Math.min(100, total / 50);
        dashboard.setPlacementReadinessScore(score);

        return ResponseEntity.ok(dashboard);
    }
}