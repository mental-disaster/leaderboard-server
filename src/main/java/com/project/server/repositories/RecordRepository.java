package com.project.server.repositories;

import com.project.server.models.LeaderboardRecord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<LeaderboardRecord, String> {

    List<LeaderboardRecord> findByOrderByScoreDescRecordedAtAsc(Pageable pageable);
}
