package com.project.server.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.math.BigInteger;

@Getter
public class LeaderboardPostDto {
    @Pattern(regexp = "^$|^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "잘못된 ID입니다.")
    private String id;
    @NotBlank()
    private String name;
    @NotNull()
    private BigInteger score;
}
