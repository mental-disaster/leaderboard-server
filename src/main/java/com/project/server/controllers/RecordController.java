package com.project.server.controllers;

import com.project.server.dtos.RecordGetDto;
import com.project.server.dtos.RecordPostDto;
import com.project.server.models.LeaderboardRecord;
import com.project.server.services.RecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/records")
public class RecordController {

    private final RecordService recordService;

    @PostMapping("")
    public ResponseEntity<LeaderboardRecord> save(@Valid @RequestBody RecordPostDto dto) {
        LeaderboardRecord newRecord = recordService.saveLeaderboard(dto);

        return new ResponseEntity<>(
                newRecord,
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecordGetDto> findById(@PathVariable("id") String id) {
        LeaderboardRecord record = recordService.findById(id);

        return new ResponseEntity<>(
                record.toGetDto(),
                HttpStatus.OK);
    }
}
