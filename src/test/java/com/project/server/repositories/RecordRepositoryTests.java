package com.project.server.repositories;

import com.project.server.models.LeaderboardRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
public class RecordRepositoryTests {

    @Autowired
    private RecordRepository recordRepository;

    @AfterEach
    public void tearDown() {
        recordRepository.deleteAll();
    }

    @Test
    public void findByOrderByScoreDescCreatedAtAsc() {
        List<LeaderboardRecord> baseDate = List.of(
                new LeaderboardRecord().setId("1").setName("11").setScore(BigInteger.valueOf(1003)).setRecordedAt(LocalDateTime.of(2024, 1, 1, 0, 0, 0)),
                new LeaderboardRecord().setId("2").setName("12").setScore(BigInteger.valueOf(1002)).setRecordedAt(LocalDateTime.of(2024, 1, 1, 0, 0, 0)),
                new LeaderboardRecord().setId("3").setName("13").setScore(BigInteger.valueOf(1001)).setRecordedAt(LocalDateTime.of(2024, 1, 1, 0, 0, 0)),
                new LeaderboardRecord().setId("4").setName("8").setScore(BigInteger.valueOf(1006)).setRecordedAt(LocalDateTime.of(2026, 1, 1, 0, 0, 0)),
                new LeaderboardRecord().setId("5").setName("9").setScore(BigInteger.valueOf(1005)).setRecordedAt(LocalDateTime.of(2024, 1, 1, 0, 0, 0)),
                new LeaderboardRecord().setId("6").setName("1").setScore(BigInteger.valueOf(1010)).setRecordedAt(LocalDateTime.of(2024, 1, 1, 0, 0, 0)),
                new LeaderboardRecord().setId("7").setName("2").setScore(BigInteger.valueOf(1010)).setRecordedAt(LocalDateTime.of(2025, 1, 1, 0, 0, 0)),
                new LeaderboardRecord().setId("8").setName("3").setScore(BigInteger.valueOf(1009)).setRecordedAt(LocalDateTime.of(2024, 1, 1, 0, 0, 0)),
                new LeaderboardRecord().setId("9").setName("4").setScore(BigInteger.valueOf(1008)).setRecordedAt(LocalDateTime.of(2024, 1, 1, 0, 0, 0)),
                new LeaderboardRecord().setId("10").setName("5").setScore(BigInteger.valueOf(1007)).setRecordedAt(LocalDateTime.of(2024, 1, 1, 0, 0, 0)),
                new LeaderboardRecord().setId("11").setName("6").setScore(BigInteger.valueOf(1006)).setRecordedAt(LocalDateTime.of(2024, 1, 1, 0, 0, 0)),
                new LeaderboardRecord().setId("12").setName("7").setScore(BigInteger.valueOf(1006)).setRecordedAt(LocalDateTime.of(2025, 1, 1, 0, 0, 0)),
                new LeaderboardRecord().setId("13").setName("10").setScore(BigInteger.valueOf(1004)).setRecordedAt(LocalDateTime.of(2024, 1, 1, 0, 0, 0)),
                new LeaderboardRecord().setId("14").setName("14").setScore(BigInteger.valueOf(1000)).setRecordedAt(LocalDateTime.of(2024, 1, 1, 0, 0, 0)),
                new LeaderboardRecord().setId("15").setName("15").setScore(BigInteger.valueOf(1))
        );

        recordRepository.saveAll(baseDate);

        Pageable pageable = PageRequest.of(0, 10);
        List<LeaderboardRecord> records = recordRepository.findByOrderByScoreDescRecordedAtAsc(pageable);

        Assertions.assertEquals(10, records.size());

        for (int i = 0; i < records.size(); i++) {
            LeaderboardRecord leaderboardRecord = records.get(i);
            Assertions.assertEquals(i+1, Integer.valueOf(leaderboardRecord.getName()));
        }
    }
}
