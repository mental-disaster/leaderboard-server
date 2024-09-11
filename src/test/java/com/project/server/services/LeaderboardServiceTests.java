package com.project.server.services;

import com.project.server.dtos.GroupPostDto;
import com.project.server.enums.ErrorEnum;
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
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LeaderboardServiceTests {

    @Mock
    private RecordRepository recordRepository;

    @InjectMocks
    private LeaderboardService leaderboardService;

    @Test
    void testFindTop10ByScore() {
        when(recordRepository.findByOrderByScoreDescRecordedAtAsc(PageRequest.of(0, 10)))
                .thenReturn(
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
    void testJoinGroup_success() {
        List<GroupPostDto> testList = List.of(
                GroupPostDto.builder().id("1").build(),
                GroupPostDto.builder().id("2").groupId("qwer123").build()
        );

        when(recordRepository.findById("1"))
                .thenReturn(
                        Optional.of(
                                new LeaderboardRecord().setId("1")
                        )
                );
        when(recordRepository.findById("2"))
                .thenReturn(
                        Optional.of(
                                new LeaderboardRecord()
                                        .setId("2")
                                        .setGroupId("abc1234")
                        )
                );
        when(recordRepository.existsByGroupId(anyString()))
                .thenAnswer(invocationOnMock -> {
                    String groupId = invocationOnMock.getArgument(0);
                    if (groupId.equals("qwer123")) {
                        return true;
                    }
                    return false;
                });
        when(recordRepository.save(any(LeaderboardRecord.class)))
                .thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        LeaderboardRecord test1 = leaderboardService.joinGroup(testList.get(0));
        Assertions.assertNotNull(test1.getGroupId());

        LeaderboardRecord test2 = leaderboardService.joinGroup(testList.get(1));
        Assertions.assertEquals("qwer123", test2.getGroupId());
    }

    @Test
    void testJoinGroup_fail() {
        List<GroupPostDto> testList = List.of(
                GroupPostDto.builder().id("1").build(),
                GroupPostDto.builder().id("2").groupId("qwer123").build(),
                GroupPostDto.builder().id("3").build()
        );

        when(recordRepository.findById(anyString()))
                .thenAnswer(invocationOnMock -> {
                    String id = invocationOnMock.getArgument(0);
                    if (id.equals("1")) {
                        return Optional.empty();
                    }
                    return Optional.of(
                            new LeaderboardRecord().setId("2")
                    );
                });
        when(recordRepository.existsByGroupId(anyString()))
                .thenAnswer(invocationOnMock -> {
                    String groupId = invocationOnMock.getArgument(0);
                    if (groupId.equals("qwer123")) {
                        return false;
                    }
                    return true;
                });

        InvalidParameterException invalidId = Assertions.assertThrows(InvalidParameterException.class, () -> leaderboardService.joinGroup(testList.get(0)));
        Assertions.assertEquals(ErrorEnum.INVALID_ID.getMessage(), invalidId.getMessage());

        InvalidParameterException invalidGroupId = Assertions.assertThrows(InvalidParameterException.class, () -> leaderboardService.joinGroup(testList.get(1)));
        Assertions.assertEquals(ErrorEnum.INVALID_PARAMETER.getMessage(), invalidGroupId.getMessage());

        IllegalStateException generateFail = Assertions.assertThrows(IllegalStateException.class, () -> leaderboardService.joinGroup(testList.get(2)));
        Assertions.assertEquals(ErrorEnum.GENERATE_FAIL.getMessage(), generateFail.getMessage());
    }

    @Test
    void testFindAllByGroupId() {
        when(recordRepository.findAllByGroupId("123"))
                .thenReturn(
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
