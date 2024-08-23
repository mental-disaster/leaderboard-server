package com.project.server.repositories;

import com.project.server.models.Leaderboard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LeaderboardRepository extends JpaRepository<Leaderboard, Long> {

    @Query("select leaderboard from Leaderboard leaderboard order by leaderboard.score desc")
    List<Leaderboard> findTopOrderByScoreDesc(Pageable pageable);
}
