package com.project.server.dtos;

import java.math.BigInteger;
import java.time.LocalDateTime;

public record RecordGetDto(String name, BigInteger score, String groupId, LocalDateTime recordedAt) { }
