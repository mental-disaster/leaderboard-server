package com.project.server.services;

import com.project.server.dtos.LeaderboardPostDto;
import com.project.server.models.Leaderboard;
import com.project.server.repositories.LeaderboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LeaderboardService {

    final LeaderboardRepository leaderboardRepository;

    public Leaderboard saveLeaderboard(LeaderboardPostDto dto) {
        Leaderboard newRecord = new Leaderboard();
        newRecord.setName(dto.getName());
        newRecord.setScore(dto.getScore());

        return leaderboardRepository.save(newRecord);
    }

    public List<Leaderboard> findTop10ByScore() {
        Pageable pageable = PageRequest.of(0, 10);

        return leaderboardRepository.findTopOrderByScoreDesc(pageable);
    }

    public Optional<Leaderboard> findById(Long id) {
        return leaderboardRepository.findById(id);
    }
}
