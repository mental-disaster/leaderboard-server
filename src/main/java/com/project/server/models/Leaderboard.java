package com.project.server.models;

import com.project.server.dtos.LeaderboardGetDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Accessors(chain = true)
public class Leaderboard {

    @Id
    private String id;
    private String name;
    private BigInteger score;
    @CreationTimestamp
    @Column(nullable = false, updatable = false, insertable = false)
    private LocalDateTime createdAt;

    public LeaderboardGetDto toGetDto() {
        return LeaderboardGetDto.builder()
                .name(this.name)
                .score(this.score)
                .createdAt(this.createdAt)
                .build();
    }
}
