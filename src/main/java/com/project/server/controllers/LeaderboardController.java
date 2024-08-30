package com.project.server.controllers;

import com.project.server.dtos.LeaderboardGetDto;
import com.project.server.dtos.LeaderboardPostDto;
import com.project.server.models.Leaderboard;
import com.project.server.services.LeaderboardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leaderboards")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @PostMapping("")
    public ResponseEntity<Leaderboard> save(@Valid @RequestBody LeaderboardPostDto dto) {
        Leaderboard newRecord = leaderboardService.saveLeaderboard(dto);

        return new ResponseEntity<>(
                newRecord,
                HttpStatus.OK);
    }

    @GetMapping("/top10")
    public ResponseEntity<List<LeaderboardGetDto>> top10() {
        List<Leaderboard> records = leaderboardService.findTop10ByScore();

        return new ResponseEntity<>(
                records.stream().map((Leaderboard::toGetDto)).toList(),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaderboardGetDto> getById(@PathVariable("id") String id) {
        Leaderboard record = leaderboardService.findById(id);

        return new ResponseEntity<>(
                record.toGetDto(),
                HttpStatus.OK);
    }
}
