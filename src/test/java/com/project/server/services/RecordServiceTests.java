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
    void testSaveRecord_success() {
        String existId = "b9c7fc7e-f2c2-4e39-85f1-123456789abc";
        BigInteger existScore = BigInteger.valueOf(1000);
        List<RecordPostDto> testList = List.of(
                RecordPostDto.builder().id(null).name("new user").score(BigInteger.valueOf(1000)).groupId("group").build(),
                RecordPostDto.builder().id("").name("new user2").score(BigInteger.valueOf(100)).build(),
                RecordPostDto.builder().id(existId).name("high score").score(BigInteger.valueOf(1500)).groupId("groupExist").build(),
                RecordPostDto.builder().id(existId).name("low score").score(BigInteger.valueOf(500)).build()
        );

        when(recordRepository.findById(anyString()))
                .thenAnswer(invocationOnMock -> {
                    String uuid = invocationOnMock.getArgument(0);
                    if (existId.equals(uuid)) {
                        return Optional.of(
                                new LeaderboardRecord()
                                        .setId(uuid)
                                        .setName("existUser")
                                        .setScore(existScore)
                                        .setRecordedAt(LocalDateTime.now())
                        );
                    } else {
                        return Optional.empty();
                    }
                });
        when(recordRepository.save(any(LeaderboardRecord.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        for (RecordPostDto leaderboard : testList) {
            LeaderboardRecord result = recordService.saveRecord(leaderboard);

            if (leaderboard.getId() == null || leaderboard.getId().isEmpty()) {
                // id가 없는 경우(새로운 기록) id 생성
                Assertions.assertNotNull(result.getId());
                Assertions.assertEquals(leaderboard.getScore(), result.getScore());
            } else {
                // 기록갱신의 경우
                Assertions.assertEquals(result.getId(), leaderboard.getId());
                if (leaderboard.getScore().compareTo(existScore) > 0) {
                    // 점수 갱신 O
                    Assertions.assertEquals(leaderboard.getScore(), result.getScore());
                } else {
                    // 점수 갱신 X
                    Assertions.assertEquals(existScore, result.getScore());
                }
            }
            Assertions.assertEquals(result.getName(), leaderboard.getName());
            Assertions.assertEquals(result.getGroupId(), leaderboard.getGroupId());
        }
    }

    @Test
    void testSaveRecord_fail() {
        String notExistId = "f4b7f5c8-2b8e-4c2a-8b6c-7f5e7d7c1b3b";
        RecordPostDto notExistIdUser = RecordPostDto.builder()
                .id(notExistId)
                .name("Unidentified id user")
                .score(BigInteger.valueOf(1000))
                .build();
        RecordPostDto newUser = RecordPostDto.builder()
                .id(null)
                .name("new user")
                .score(BigInteger.valueOf(1000))
                .build();

        when(recordRepository.findById(anyString())).thenAnswer(
                invocationOnMock -> {
                    String uuid = invocationOnMock.getArgument(0);
                    if (notExistId.equals(uuid)) {
                        return Optional.empty();
                    } else {
                        return Optional.of(new LeaderboardRecord());
                    }
                });

        InvalidParameterException invalidId = Assertions.assertThrows(InvalidParameterException.class, () -> recordService.saveRecord(notExistIdUser));
        Assertions.assertEquals(ErrorEnum.INVALID_ID.getMessage(), invalidId.getMessage());

        IllegalStateException generateFail = Assertions.assertThrows(IllegalStateException.class, () -> recordService.saveRecord(newUser));
        Assertions.assertEquals(ErrorEnum.GENERATE_FAIL.getMessage(), generateFail.getMessage());
    }

    @Test
    void testFindById_success() {
        when(recordRepository.findById(anyString()))
                .thenAnswer(invocationOnMock -> Optional.of(new LeaderboardRecord().setId(invocationOnMock.getArgument(0)).setName("user").setScore(BigInteger.valueOf(1000))));

        LeaderboardRecord result = recordService.findById("id");

        Assertions.assertEquals("id", result.getId());
        Assertions.assertEquals("user", result.getName());
        Assertions.assertEquals(BigInteger.valueOf(1000), result.getScore());
    }

    @Test
    void testFindById_fail() {
        when(recordRepository.findById(anyString()))
                .thenReturn(
                        Optional.empty()
                );

        EntityNotFoundException notFound = Assertions.assertThrows(EntityNotFoundException.class, () -> recordService.findById("id"));

        Assertions.assertEquals(ErrorEnum.NOT_FOUND_RECORD.getMessage(), notFound.getMessage());
    }
}
