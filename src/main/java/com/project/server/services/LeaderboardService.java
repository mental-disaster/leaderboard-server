package com.project.server.services;

import com.project.server.dtos.GroupPostDto;
import com.project.server.enums.ErrorEnum;
import com.project.server.models.LeaderboardRecord;
import com.project.server.repositories.RecordRepository;
import com.project.server.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LeaderboardService {

    final RecordRepository recordRepository;

    public List<LeaderboardRecord> findTop10() {
        Pageable pageable = PageRequest.of(0, 10);

        return recordRepository.findByOrderByScoreDescRecordedAtAsc(pageable);
    }

    public LeaderboardRecord joinGroup(GroupPostDto dto) {
        Optional<LeaderboardRecord> recordOptional = recordRepository.findById(dto.getId());
        if (recordOptional.isEmpty()) {
            throw new InvalidParameterException(ErrorEnum.INVALID_ID.getMessage());
        }
        LeaderboardRecord record = recordOptional.get();

        String groupId = dto.getGroupId();
        if (groupId == null) {
            int generateCount = 0;
            int generateLimit = 10;

            do {
                groupId = StringUtil.generateRandomAlphanumeric(7);
                generateCount++;
                if (generateCount > generateLimit) {
                    throw new IllegalStateException(ErrorEnum.GENERATE_FAIL.getMessage());
                }
            } while (recordRepository.existsByGroupId(groupId));
        } else if (!recordRepository.existsByGroupId(groupId)) {
            throw new InvalidParameterException(ErrorEnum.INVALID_PARAMETER.getMessage());
        }

        record.setGroupId(groupId);

        return recordRepository.save(record);
    }

    public List<LeaderboardRecord> findAllByGroupId(String groupId) {
        return recordRepository.findAllByGroupId(groupId);
    }
}
