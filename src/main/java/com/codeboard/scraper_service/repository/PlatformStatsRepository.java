package com.codeboard.scraper_service.repository;

import com.codeboard.scraper_service.entity.PlatformStats;
import com.codeboard.scraper_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlatformStatsRepository extends JpaRepository<PlatformStats, Long> {
    List<PlatformStats> findByUser(User user);
    Optional<PlatformStats> findByUserAndPlatform(User user, String platform);
}