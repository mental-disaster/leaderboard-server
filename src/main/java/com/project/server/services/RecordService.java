package com.project.server.services;

import com.project.server.enums.ErrorEnum;
import com.project.server.dtos.RecordPostDto;
import com.project.server.models.LeaderboardRecord;
import com.project.server.repositories.RecordRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecordService {

    final RecordRepository recordRepository;

    public LeaderboardRecord saveLeaderboard(RecordPostDto dto) throws InvalidParameterException {
        LeaderboardRecord newRecord = new LeaderboardRecord();
        newRecord.setName(dto.getName());
        newRecord.setScore(dto.getScore());
        newRecord.setRecordedAt(LocalDateTime.now());

        String id;
        if (dto.getId() != null && !dto.getId().isBlank()) {
            id = dto.getId();
            if (recordRepository.findById(id).isEmpty()) {
                throw new InvalidParameterException(ErrorEnum.INVALID_ID.getMessage());
            }
        } else {
            int generateCount = 0;
            int generateLimit = 10;

            do {
                id = UUID.randomUUID().toString();
                generateCount++;
                if (generateCount > generateLimit) {
                    throw new IllegalStateException(ErrorEnum.GENERATE_FAIL.getMessage());
                }
            } while (recordRepository.findById(id).isPresent());
        }
        newRecord.setId(id);

        return recordRepository.save(newRecord);
    }

    public LeaderboardRecord findById(String id) {
        Optional<LeaderboardRecord> result = recordRepository.findById(id);

        if (result.isEmpty()) {
            throw new EntityNotFoundException(ErrorEnum.NOT_FOUND_RECORD.getMessage());
        }

        return result.get();
    }
}
