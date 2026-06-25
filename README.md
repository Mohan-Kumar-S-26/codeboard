# CodeBoard 📊
Multi-platform competitive programming stats aggregator built with **Spring Boot + Redis + PostgreSQL**.

Aggregates coding statistics from LeetCode, Codeforces, HackerRank, and SkillRack into a unified dashboard.

---

## Tech Stack

| Layer        | Technology                     |
|--------------|--------------------------------|
| Backend      | Java 17, Spring Boot 3         |
| Frontend     | React.js                       |
| Cache        | Redis                          |
| Database     | PostgreSQL + Spring Data JPA   |
| Build        | Maven + Docker Compose         |

---

## How it works

1. User registers with their usernames across platforms.
2. Backend fetches stats from LeetCode, Codeforces, HackerRank and SkillRack APIs.
3. Results are **cached in Redis** to reduce repeated external API calls and avoid rate limits.
4. User profiles and score history are persisted in PostgreSQL via Spring Data JPA.
5. Unified dashboard returns aggregated stats in a single response.

---

## Frontend

Basic React frontend for viewing aggregated coding stats across platforms.  
Connects to the Spring Boot REST API.

---

## Key Design Decisions

- **Redis caching** — Platform APIs have strict rate limits. Redis stores profiles with TTL, avoiding redundant external calls.
- **PostgreSQL persistence** — Tracks score progression over time, not just current stats.
- **Unified API** — Single endpoint replaces manual checks across 4 platforms.

---

## Setup (Local)

### 1. Prerequisites
- Java 17+
- Docker (for Redis + PostgreSQL)

### 2. Start services
```bash
docker-compose up -d
```

### 3. Run
```bash
mvn spring-boot:run
```

---

## API Reference

| Method | Endpoint                  | Description                    |
|--------|---------------------------|--------------------------------|
| POST   | `/api/users`              | Register with platform handles |
| GET    | `/api/users/{id}/stats`   | Fetch aggregated stats         |
| GET    | `/api/users/{id}/history` | Score progression over time    |

---

## Upcoming Features
- Enhance React dashboard with progress charts and platform breakdown
- Weekly summary email reports
- Kafka async processing for stat updates
- Rate limiting per user
