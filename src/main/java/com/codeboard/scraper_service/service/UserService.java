package com.codeboard.scraper_service.service;

import com.codeboard.scraper_service.entity.PlatformStats;
import com.codeboard.scraper_service.entity.User;
import com.codeboard.scraper_service.repository.PlatformStatsRepository;
import com.codeboard.scraper_service.repository.UserRepository;
import com.codeboard.scraper_service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PlatformStatsRepository platformStatsRepository;
    private final LeetCodeService leetCodeService;
    private final CodeForcesService codeForcesService;
    private final HackerRankService hackerRankService;

    public User registerUser(String name, String email, String college) {
        return userRepository.findByEmail(email).orElseGet(() -> {
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setCollege(college);
            return userRepository.save(user);
        });
    }

    public void saveAllStats(Long userId,
                             String leetcodeUsername,
                             String codeforcesUsername,
                             String hackerrankUsername) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // LeetCode
        if (leetcodeUsername != null) {
            LeetCodeStatsDto lc = leetCodeService.getStats(leetcodeUsername);
            savePlatformStats(user, "LEETCODE", leetcodeUsername,
                    lc.getTotalSolved(), lc.getRanking(), 0, null);
        }

        // Codeforces
        if (codeforcesUsername != null) {
            CodeForcesStatsDto cf = codeForcesService.getStats(codeforcesUsername);
            savePlatformStats(user, "CODEFORCES", codeforcesUsername,
                    cf.getProblemsSolved(), cf.getRating(), 0, cf.getRank());
        }

        // HackerRank
        if (hackerrankUsername != null) {
            HackerRankStatsDto hr = hackerRankService.getStats(hackerrankUsername);
            savePlatformStats(user, "HACKERRANK", hackerrankUsername,
                    0, 0, 0, hr.getTotalStars());
        }
    }

    private void savePlatformStats(User user, String platform,
                                   String username, int solved,
                                   int rating, int contests, String badges) {

        PlatformStats stats = platformStatsRepository
                .findByUserAndPlatform(user, platform)
                .orElse(new PlatformStats());

        stats.setUser(user);
        stats.setPlatform(platform);
        stats.setUsername(username);
        stats.setProblemsSolved(solved);
        stats.setRating(rating);
        stats.setContests(contests);
        stats.setBadges(badges);

        platformStatsRepository.save(stats);
    }

    public List<PlatformStats> getUserStats(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return platformStatsRepository.findByUser(user);
    }
}