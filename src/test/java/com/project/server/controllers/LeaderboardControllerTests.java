package com.project.server.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.server.dtos.LeaderboardPostDto;
import com.project.server.models.Leaderboard;
import com.project.server.services.LeaderboardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigInteger;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = LeaderboardController.class)
public class LeaderboardControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LeaderboardService leaderboardService;

    @Test
    public void testSave_success() throws Exception {
        List<LeaderboardPostDto> testList = List.of(
                LeaderboardPostDto.builder().id(null).name("no id user").score(BigInteger.valueOf(1000)).build(),
                LeaderboardPostDto.builder().id("b9c7fc7e-f2c2-4e39-85f1-123456789abc").name("has id user").score(BigInteger.ONE).build()
        );

        when(leaderboardService.saveLeaderboard(any(LeaderboardPostDto.class))).thenAnswer(invocationOnMock -> {
            LeaderboardPostDto dto = invocationOnMock.getArgument(0);
            return new Leaderboard()
                    .setId("id")
                    .setName(dto.getName())
                    .setScore(dto.getScore());
        });

        for (LeaderboardPostDto bodyParam : testList) {
            String bodyJson = objectMapper.writeValueAsString(bodyParam);

            mockMvc.perform(post("/leaderboards")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(bodyJson))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").isNotEmpty())
                    .andExpect(jsonPath("$.name").value(bodyParam.getName()))
                    .andExpect(jsonPath("$.score").value(bodyParam.getScore()));
        }
    }

    @Test
    public void testSave_fail_invalidParam() throws Exception {
        String bodyJson = objectMapper.writeValueAsString(
                LeaderboardPostDto.builder().id("invalid param").name("has id user").score(BigInteger.ONE).build()
        );

        mockMvc.perform(post("/leaderboards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testTop10() throws Exception {
        when(leaderboardService.findTop10()).thenReturn(
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

        mockMvc.perform(get("/leaderboards/top10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(10));
    }

    @Test
    public void testFindById_success() throws Exception {
        when(leaderboardService.findById("1")).thenReturn(
                new Leaderboard().setId("1").setName("user1").setScore(BigInteger.ONE)
        );

        mockMvc.perform(get("/leaderboards/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("user1"))
                .andExpect(jsonPath("$.score").value(BigInteger.ONE));
    }
}
