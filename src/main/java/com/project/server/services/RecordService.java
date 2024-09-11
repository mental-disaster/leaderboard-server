package com.project.server.services;

import com.project.server.enums.ErrorEnum;
import com.project.server.dtos.RecordPostDto;
import com.project.server.models.LeaderboardRecord;
import com.project.server.repositories.RecordRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecordService {

    final RecordRepository recordRepository;

    @Caching(evict = {
            @CacheEvict(cacheNames = "leaderboardCache", key = "'record_' + #dto.id", condition = "#dto.id != null"),
            @CacheEvict(cacheNames = "leaderboardCache", key = "'group_' + #dto.groupId", condition = "#dto.groupId != null")
    })
    public LeaderboardRecord saveRecord(RecordPostDto dto) throws InvalidParameterException {
        LeaderboardRecord newRecord = new LeaderboardRecord();
        newRecord.setName(dto.getName());

        String id;
        BigInteger score;
        LocalDateTime recordedAt;
        if (dto.getId() != null && !dto.getId().isBlank()) {
            id = dto.getId();
            Optional<LeaderboardRecord> existRecord = recordRepository.findById(id);

            if (existRecord.isEmpty()) {
                throw new InvalidParameterException(ErrorEnum.INVALID_ID.getMessage());
            }

            if (dto.getScore().compareTo(existRecord.get().getScore()) > 0) {
                score = dto.getScore();
                recordedAt = LocalDateTime.now();
            } else {
                score = existRecord.get().getScore();
                recordedAt = existRecord.get().getRecordedAt();
            }
        } else {
            int generateCount = 0;
            int generateLimit = 10;

            // 기록 id 생성 시도
            // generateLimit 안에 중복되지 않은 key 생성 실패할 경우 오류 출력
            do {
                id = UUID.randomUUID().toString();
                generateCount++;
                if (generateCount > generateLimit) {
                    throw new IllegalStateException(ErrorEnum.GENERATE_FAIL.getMessage());
                }
            } while (recordRepository.findById(id).isPresent());

            score = dto.getScore();
            recordedAt = LocalDateTime.now();
        }
        newRecord.setId(id);
        newRecord.setScore(score);
        newRecord.setGroupId(dto.getGroupId());
        newRecord.setRecordedAt(recordedAt);

        return recordRepository.save(newRecord);
    }

    @Cacheable(cacheNames = "leaderboardCache", key = "'record_' + #id")
    public LeaderboardRecord findById(String id) {
        Optional<LeaderboardRecord> result = recordRepository.findById(id);

        if (result.isEmpty()) {
            throw new EntityNotFoundException(ErrorEnum.NOT_FOUND_RECORD.getMessage());
        }

        return result.get();
    }
}
