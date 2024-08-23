package com.project.server.controllers;

import com.project.server.dtos.LeaderboardPostDto;
import com.project.server.models.Leaderboard;
import com.project.server.services.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leaderboard")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody LeaderboardPostDto dto) {
        Leaderboard newRecord = leaderboardService.saveLeaderboard(dto);

        return new ResponseEntity<>(newRecord, HttpStatus.OK);
    }

    @GetMapping("/top10")
    public ResponseEntity<?> top10() {
        List<Leaderboard> data = leaderboardService.findTop10ByScore();

        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
