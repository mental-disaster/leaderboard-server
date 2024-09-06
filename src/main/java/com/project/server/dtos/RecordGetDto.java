package com.project.server.dtos;

import lombok.Builder;
import lombok.Getter;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Builder
public class RecordGetDto {
    private String name;
    private BigInteger score;
    private String groupId;
    private LocalDateTime recordedAt;
}
