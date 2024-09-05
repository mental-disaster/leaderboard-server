package com.project.server.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.server.dtos.RecordPostDto;
import com.project.server.models.LeaderboardRecord;
import com.project.server.services.RecordService;
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

@WebMvcTest(controllers = RecordController.class)
public class RecordControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RecordService recordService;

    @Test
    public void testSave_success() throws Exception {
        List<RecordPostDto> testList = List.of(
                RecordPostDto.builder().id(null).name("no id user").score(BigInteger.valueOf(1000)).build(),
                RecordPostDto.builder().id("b9c7fc7e-f2c2-4e39-85f1-123456789abc").name("has id user").score(BigInteger.ONE).build()
        );

        when(recordService.saveLeaderboard(any(RecordPostDto.class))).thenAnswer(invocationOnMock -> {
            RecordPostDto dto = invocationOnMock.getArgument(0);
            return new LeaderboardRecord()
                    .setId("id")
                    .setName(dto.getName())
                    .setScore(dto.getScore());
        });

        for (RecordPostDto bodyParam : testList) {
            String bodyJson = objectMapper.writeValueAsString(bodyParam);

            mockMvc.perform(post("/records")
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
                RecordPostDto.builder().id("invalid param").name("has id user").score(BigInteger.ONE).build()
        );

        mockMvc.perform(post("/records")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testFindById_success() throws Exception {
        when(recordService.findById("1")).thenReturn(
                new LeaderboardRecord().setId("1").setName("user1").setScore(BigInteger.ONE)
        );

        mockMvc.perform(get("/records/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("user1"))
                .andExpect(jsonPath("$.score").value(BigInteger.ONE));
    }
}
