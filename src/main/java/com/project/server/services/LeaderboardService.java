package com.project.server.services;

import com.project.server.models.LeaderboardRecord;
import com.project.server.repositories.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaderboardService {

    final RecordRepository recordRepository;

    public List<LeaderboardRecord> findTop10() {
        Pageable pageable = PageRequest.of(0, 10);

        return recordRepository.findByOrderByScoreDescRecordedAtAsc(pageable);
    }

    public List<LeaderboardRecord> findAllByGroupId(String groupId) {
        return recordRepository.findAllByGroupId(groupId);
    }
}
