package com.project.server.repositories;

import com.project.server.models.Leaderboard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaderboardRepository extends JpaRepository<Leaderboard, String> {

    List<Leaderboard> findByOrderByScoreDescRecordedAtAsc(Pageable pageable);
}
