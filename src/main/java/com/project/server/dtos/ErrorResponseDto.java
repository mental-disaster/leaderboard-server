package com.project.server.dtos;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponseDto {
    private String message;
    private String errorCode;
    private LocalDateTime timestamp;
}
