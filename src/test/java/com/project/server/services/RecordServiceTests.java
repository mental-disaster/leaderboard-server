package com.project.server.services;

import com.project.server.enums.ErrorEnum;
import com.project.server.dtos.RecordPostDto;
import com.project.server.models.LeaderboardRecord;
import com.project.server.repositories.RecordRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecordServiceTests {

    @Mock
    private RecordRepository recordRepository;

    @InjectMocks
    private RecordService recordService;

    @Test
    void testSaveLeaderboard_success() {
        String existId = "b9c7fc7e-f2c2-4e39-85f1-123456789abc";
        List<RecordPostDto> testList = List.of(
                RecordPostDto.builder().id(null).name("new user").score(BigInteger.valueOf(1000)).build(),
                RecordPostDto.builder().id(existId).name("good record").score(BigInteger.valueOf(1500)).build()
        );

        when(recordRepository.findById(anyString())).thenAnswer(invocationOnMock -> {
            String uuid = invocationOnMock.getArgument(0);
            if (existId.equals(uuid)) {
                return Optional.of(
                        new LeaderboardRecord()
                                .setId(uuid)
                                .setName("user1")
                                .setScore(BigInteger.valueOf(1000))
                                .setRecordedAt(LocalDateTime.now())
                );
            } else {
                return Optional.empty();
            }
        });
        when(recordRepository.save(any(LeaderboardRecord.class))).thenAnswer(invocationOnMock ->
                invocationOnMock.getArgument(0)
        );

        for (RecordPostDto leaderboard : testList) {
            LeaderboardRecord result = recordService.saveLeaderboard(leaderboard);

            if (leaderboard.getId() == null || leaderboard.getId().isEmpty()) {
                Assertions.assertNotNull(result.getId());
            } else {
                Assertions.assertEquals(result.getId(), leaderboard.getId());
            }
            Assertions.assertEquals(result.getName(), leaderboard.getName());
            Assertions.assertEquals(result.getScore(), leaderboard.getScore());
        }
    }

    @Test
    void testSaveLeaderboard_fail_notExistId() {
        String notExistId = "f4b7f5c8-2b8e-4c2a-8b6c-7f5e7d7c1b3b";
        RecordPostDto notExistIdUser = RecordPostDto.builder()
                .id(notExistId)
                .name("Unidentified id user")
                .score(BigInteger.valueOf(1000))
                .build();

        when(recordRepository.findById(notExistId)).thenReturn(
                Optional.empty()
        );

        InvalidParameterException thrown = Assertions.assertThrows(InvalidParameterException.class, () -> recordService.saveLeaderboard(notExistIdUser));

        Assertions.assertEquals(ErrorEnum.INVALID_ID.getMessage(), thrown.getMessage());
    }

    @Test
    void testFindById_success() {
        when(recordRepository.findById(anyString())).thenAnswer(invocationOnMock ->
                Optional.of(new LeaderboardRecord().setId(invocationOnMock.getArgument(0)).setName("user").setScore(BigInteger.valueOf(1000)))
        );

        LeaderboardRecord result = recordService.findById("id");

        Assertions.assertEquals("id", result.getId());
        Assertions.assertEquals("user", result.getName());
        Assertions.assertEquals(BigInteger.valueOf(1000), result.getScore());
    }

    @Test
    void testFindById_fail_notFound() {
        when(recordRepository.findById(anyString())).thenReturn(
                Optional.empty()
        );

        EntityNotFoundException thrown = Assertions.assertThrows(EntityNotFoundException.class, () -> recordService.findById("id"));

        Assertions.assertEquals(ErrorEnum.NOT_FOUND_LEADERBOARD.getMessage(), thrown.getMessage());
    }
}
