package com.codeboard.scraper_service.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "platform_stats")
public class PlatformStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String platform; // LEETCODE, HACKERRANK, SKILLRACK, CODEFORCES

    @Column
    private String username;

    @Column(name = "problems_solved")
    private Integer problemsSolved;

    @Column
    private Integer rating;

    @Column
    private Integer contests;

    @Column
    private String badges;

    @Column(name = "fetched_at")
    private LocalDateTime fetchedAt;

    @PrePersist
    @PreUpdate
    public void prePersist() {
        fetchedAt = LocalDateTime.now();
    }
}