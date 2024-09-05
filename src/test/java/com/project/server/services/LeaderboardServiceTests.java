package com.project.server.services;

import com.project.server.models.LeaderboardRecord;
import com.project.server.repositories.RecordRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.math.BigInteger;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LeaderboardServiceTests {

    @Mock
    private RecordRepository recordRepository;

    @InjectMocks
    private LeaderboardService leaderboardService;

    @Test
    void testFindTop10ByScore() {
        when(recordRepository.findByOrderByScoreDescRecordedAtAsc(PageRequest.of(0, 10))).thenReturn(
                List.of(
                        new LeaderboardRecord().setId("1").setName("user1").setScore(BigInteger.valueOf(1)),
                        new LeaderboardRecord().setId("2").setName("user2").setScore(BigInteger.valueOf(2)),
                        new LeaderboardRecord().setId("3").setName("user3").setScore(BigInteger.valueOf(3)),
                        new LeaderboardRecord().setId("4").setName("user4").setScore(BigInteger.valueOf(4)),
                        new LeaderboardRecord().setId("5").setName("user5").setScore(BigInteger.valueOf(5)),
                        new LeaderboardRecord().setId("6").setName("user6").setScore(BigInteger.valueOf(6)),
                        new LeaderboardRecord().setId("7").setName("user7").setScore(BigInteger.valueOf(7)),
                        new LeaderboardRecord().setId("8").setName("user8").setScore(BigInteger.valueOf(8)),
                        new LeaderboardRecord().setId("9").setName("user9").setScore(BigInteger.valueOf(9)),
                        new LeaderboardRecord().setId("10").setName("user10").setScore(BigInteger.valueOf(10))
                )
        );

        List<LeaderboardRecord> result = leaderboardService.findTop10();

        Assertions.assertEquals(10, result.size());
    }

    @Test
    void testFindAllByGroupId() {
        when(recordRepository.findAllByGroupId("123")).thenReturn(
                List.of(
                        new LeaderboardRecord().setId("1").setName("user1").setScore(BigInteger.valueOf(1)).setGroupId("123"),
                        new LeaderboardRecord().setId("2").setName("user2").setScore(BigInteger.valueOf(2)).setGroupId("123"),
                        new LeaderboardRecord().setId("3").setName("user3").setScore(BigInteger.valueOf(3)).setGroupId("123")
                )
        );

        List<LeaderboardRecord> result = leaderboardService.findAllByGroupId("123");

        Assertions.assertEquals(3, result.size());
    }
}
