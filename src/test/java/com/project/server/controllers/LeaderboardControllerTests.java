package com.project.server.controllers;

import com.project.server.models.LeaderboardRecord;
import com.project.server.services.LeaderboardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigInteger;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LeaderboardController.class)
public class LeaderboardControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LeaderboardService leaderboardService;

    @Test
    public void testTop10() throws Exception {
        when(leaderboardService.findTop10()).thenReturn(
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

        mockMvc.perform(get("/leaderboards/top10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(10));
    }

    @Test
    public void testFindAllByGroupId() throws Exception {
        when(leaderboardService.findAllByGroupId("123")).thenReturn(
                List.of(
                        new LeaderboardRecord().setId("1").setName("user1").setScore(BigInteger.valueOf(1)).setGroupId("123"),
                        new LeaderboardRecord().setId("2").setName("user2").setScore(BigInteger.valueOf(2)).setGroupId("123"),
                        new LeaderboardRecord().setId("3").setName("user3").setScore(BigInteger.valueOf(3)).setGroupId("123")
                )
        );

        mockMvc.perform(get("/leaderboards/groups/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }
}
