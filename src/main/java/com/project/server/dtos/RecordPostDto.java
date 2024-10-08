package com.project.server.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

import java.math.BigInteger;

@Getter
@Builder
public class RecordPostDto {
    @Pattern(regexp = "^$|^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "잘못된 ID 형식입니다.")
    private String id;
    @NotBlank()
    private String name;
    @NotNull()
    private BigInteger score;
    private String groupId;
}
