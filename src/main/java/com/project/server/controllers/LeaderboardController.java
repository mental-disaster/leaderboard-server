package com.project.server.controllers;

import com.project.server.dtos.RecordGetDto;
import com.project.server.models.LeaderboardRecord;
import com.project.server.services.LeaderboardService;
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

    @GetMapping("/top10")
    public ResponseEntity<List<RecordGetDto>> top10() {
        List<LeaderboardRecord> records = leaderboardService.findTop10();

        return new ResponseEntity<>(
                records.stream().map((LeaderboardRecord::toGetDto)).toList(),
                HttpStatus.OK);
    }
}
