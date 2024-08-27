package com.project.server.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigInteger;

@Getter
public class LeaderboardPostDto {
    private String id;
    @NotBlank()
    private String name;
    @NotNull()
    private BigInteger score;
}
