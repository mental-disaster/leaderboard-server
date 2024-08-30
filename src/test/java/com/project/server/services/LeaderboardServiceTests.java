package com.project.server.services;

import com.project.server.enums.ErrorEnum;
import com.project.server.dtos.LeaderboardPostDto;
import com.project.server.models.Leaderboard;
import com.project.server.repositories.LeaderboardRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.math.BigInteger;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LeaderboardServiceTests {

    @Mock
    private LeaderboardRepository leaderboardRepository;

    @InjectMocks
    private LeaderboardService leaderboardService;

    @Test
    void testSaveLeaderboard_success() {
        String existId = "b9c7fc7e-f2c2-4e39-85f1-123456789abc";
        List<LeaderboardPostDto> testList = List.of(
                LeaderboardPostDto.builder().id(null).name("new user").score(BigInteger.valueOf(1000)).build(),
                LeaderboardPostDto.builder().id(existId).name("good record").score(BigInteger.valueOf(1500)).build()
        );

        when(leaderboardRepository.findById(anyString())).thenAnswer(invocationOnMock -> {
            String uuid = invocationOnMock.getArgument(0);
            if (existId.equals(uuid)) {
                return Optional.of(
                        new Leaderboard()
                                .setId(uuid)
                                .setName("user1")
                                .setScore(BigInteger.valueOf(1000))
                                .setRecordedAt(LocalDateTime.now())
                );
            } else {
                return Optional.empty();
            }
        });
        when(leaderboardRepository.save(any(Leaderboard.class))).thenAnswer(invocationOnMock ->
                invocationOnMock.getArgument(0)
        );

        for (LeaderboardPostDto leaderboard : testList) {
            Leaderboard result = leaderboardService.saveLeaderboard(leaderboard);

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
        LeaderboardPostDto notExistIdUser = LeaderboardPostDto.builder()
                .id(notExistId)
                .name("Unidentified id user")
                .score(BigInteger.valueOf(1000))
                .build();

        when(leaderboardRepository.findById(notExistId)).thenReturn(
                Optional.empty()
        );

        InvalidParameterException thrown = Assertions.assertThrows(InvalidParameterException.class, () -> leaderboardService.saveLeaderboard(notExistIdUser));

        Assertions.assertEquals(ErrorEnum.INVALID_ID.getMessage(), thrown.getMessage());
    }

    @Test
    void testFindTop10ByScore() {
        when(leaderboardRepository.findByOrderByScoreDescRecordedAtAsc(PageRequest.of(0, 10))).thenReturn(
                List.of(
                        new Leaderboard().setId("1").setName("user1").setScore(BigInteger.valueOf(1)),
                        new Leaderboard().setId("2").setName("user2").setScore(BigInteger.valueOf(2)),
                        new Leaderboard().setId("3").setName("user3").setScore(BigInteger.valueOf(3)),
                        new Leaderboard().setId("4").setName("user4").setScore(BigInteger.valueOf(4)),
                        new Leaderboard().setId("5").setName("user5").setScore(BigInteger.valueOf(5)),
                        new Leaderboard().setId("6").setName("user6").setScore(BigInteger.valueOf(6)),
                        new Leaderboard().setId("7").setName("user7").setScore(BigInteger.valueOf(7)),
                        new Leaderboard().setId("8").setName("user8").setScore(BigInteger.valueOf(8)),
                        new Leaderboard().setId("9").setName("user9").setScore(BigInteger.valueOf(9)),
                        new Leaderboard().setId("10").setName("user10").setScore(BigInteger.valueOf(10))
                )
        );

        List<Leaderboard> result = leaderboardService.findTop10();

        Assertions.assertEquals(10, result.size());
    }

    @Test
    void testFindById_success() {
        when(leaderboardRepository.findById(anyString())).thenAnswer(invocationOnMock ->
                Optional.of(new Leaderboard().setId(invocationOnMock.getArgument(0)).setName("user").setScore(BigInteger.valueOf(1000)))
        );

        Leaderboard result = leaderboardService.findById("id");

        Assertions.assertEquals("id", result.getId());
        Assertions.assertEquals("user", result.getName());
        Assertions.assertEquals(BigInteger.valueOf(1000), result.getScore());
    }

    @Test
    void testFindById_fail_notFound() {
        when(leaderboardRepository.findById(anyString())).thenReturn(
                Optional.empty()
        );

        EntityNotFoundException thrown = Assertions.assertThrows(EntityNotFoundException.class, () -> leaderboardService.findById("id"));

        Assertions.assertEquals(ErrorEnum.NOT_FOUND_LEADERBOARD.getMessage(), thrown.getMessage());
    }
}
