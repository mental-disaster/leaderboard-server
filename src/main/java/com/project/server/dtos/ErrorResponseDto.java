package com.project.server.dtos;

import java.time.LocalDateTime;

public record ErrorResponseDto(int errorCode, String message, LocalDateTime timestamp) { }
