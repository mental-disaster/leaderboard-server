package com.project.server.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigInteger;

public record RecordPostDto (
        @Pattern(regexp = "^$|^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "잘못된 ID 형식입니다.")
        String id,

        @NotBlank()
        String name,

        @NotNull()
        BigInteger score,

        String groupId
) { }
