package com.project.server.models;

import com.project.server.dtos.RecordGetDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Accessors(chain = true)
public class LeaderboardRecord {

    @Id
    private String id;
    private String name;
    private BigInteger score;
    private LocalDateTime recordedAt;

    public RecordGetDto toGetDto() {
        return RecordGetDto.builder()
                .name(this.name)
                .score(this.score)
                .recordedAt(this.recordedAt)
                .build();
    }
}
