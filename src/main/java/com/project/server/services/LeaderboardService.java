package com.project.server.services;

import com.project.server.enums.ErrorEnum;
import com.project.server.dtos.LeaderboardPostDto;
import com.project.server.models.Leaderboard;
import com.project.server.repositories.LeaderboardRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LeaderboardService {

    final LeaderboardRepository leaderboardRepository;

    public Leaderboard saveLeaderboard(LeaderboardPostDto dto) throws InvalidParameterException {
        Leaderboard newRecord = new Leaderboard();
        newRecord.setName(dto.getName());
        newRecord.setScore(dto.getScore());
        newRecord.setCreatedAt(LocalDateTime.now());

        String id;
        if (dto.getId() != null && !dto.getId().isBlank()) {
            id = dto.getId();
            if (leaderboardRepository.findById(id).isEmpty()) {
                throw new InvalidParameterException(ErrorEnum.INVALID_ID.getMessage());
            }
        } else {
            do {
                id = UUID.randomUUID().toString();
            } while (leaderboardRepository.findById(id).isPresent());
        }
        newRecord.setId(id);

        return leaderboardRepository.save(newRecord);
    }

    public List<Leaderboard> findTop10() {
        Pageable pageable = PageRequest.of(0, 10);

        return leaderboardRepository.findByOrderByScoreDescCreatedAtAsc(pageable);
    }

    public Leaderboard findById(String id) {
        Optional<Leaderboard> result = leaderboardRepository.findById(id);

        if (result.isEmpty()) {
            throw new EntityNotFoundException(ErrorEnum.NOT_FOUND_LEADERBOARD.getMessage());
        }

        return result.get();
    }
}
