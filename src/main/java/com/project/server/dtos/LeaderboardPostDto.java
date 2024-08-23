package com.project.server.dtos;

import lombok.Getter;

import java.math.BigInteger;

@Getter
public class LeaderboardPostDto {
    private String name;
    private BigInteger score;
}
