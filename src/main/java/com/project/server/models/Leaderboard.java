package com.project.server.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Leaderboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigInteger score;
    @CreationTimestamp
    @Column(nullable = false, updatable = false, insertable = false)
    private LocalDateTime createdAt;
}
