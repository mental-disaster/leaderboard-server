package com.project.server.controllers;

import com.project.server.dtos.RecordGetDto;
import com.project.server.dtos.RecordPostDto;
import com.project.server.models.LeaderboardRecord;
import com.project.server.services.RecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/records")
public class RecordController {

    private final RecordService recordService;

    /**
     * 기록 생성 또는 갱신
     * @param dto 기록 저장 요청 dto
     * @return 저장된 기록값(생성시 기록 id 포함)
     */
    @PostMapping("")
    public ResponseEntity<LeaderboardRecord> save(@Valid @RequestBody RecordPostDto dto) {
        LeaderboardRecord newRecord = recordService.saveRecord(dto);

        return ResponseEntity.ok(
                newRecord
        );
    }

    /**
     * id 기반 기록 확인
     * @param id 기록 id
     * @return 요청 기록
     */
    @GetMapping("/{id}")
    public ResponseEntity<RecordGetDto> findById(@PathVariable("id") String id) {
        LeaderboardRecord record = recordService.findById(id);

        return ResponseEntity.ok(
                record.toGetDto()
        );
    }
}
