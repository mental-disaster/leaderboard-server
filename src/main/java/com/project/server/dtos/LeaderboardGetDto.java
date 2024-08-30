package com.project.server.dtos;

import lombok.Builder;
import lombok.Getter;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Builder
public class LeaderboardGetDto {
    private String name;
    private BigInteger score;
    private LocalDateTime recordedAt;
}
