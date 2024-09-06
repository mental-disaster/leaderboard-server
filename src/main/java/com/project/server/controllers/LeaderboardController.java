package com.project.server.controllers;

import com.project.server.dtos.GroupPostDto;
import com.project.server.dtos.RecordGetDto;
import com.project.server.models.LeaderboardRecord;
import com.project.server.services.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/leaderboards")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    /**
     * 리더보드에 등록된 상위 10개 기록
     * @return 상위 10개 기록 리스트
     */
    @GetMapping("/top10")
    public ResponseEntity<List<RecordGetDto>> top10() {
        List<LeaderboardRecord> records = leaderboardService.findTop10();

        return ResponseEntity.ok(
                records.stream().map((LeaderboardRecord::toGetDto)).toList()
        );
    }

    /**
     * 리더보드 그룹 등록
     * @param dto 그룹 생성 요청 dto
     * @return 새로운 그룹 id를 포함한 기록
     */
    @PostMapping("/groups")
    public ResponseEntity<RecordGetDto> joinGroup(@RequestBody GroupPostDto dto) {
        LeaderboardRecord record = leaderboardService.joinGroup(dto);

        return ResponseEntity.ok(
                record.toGetDto()
        );
    }

    /**
     * 리더보드 그룹 기록 확인
     * @param groupId 요청 그룹 id
     * @return 해당 그룹 기록 전체 리스트
     */
    @GetMapping("/groups/{id}")
    public ResponseEntity<List<RecordGetDto>> findAllByGroupId(@PathVariable("id") String groupId) {
        List<LeaderboardRecord> records = leaderboardService.findAllByGroupId(groupId);

        return ResponseEntity.ok(
                records.stream().map((LeaderboardRecord::toGetDto)).toList()
        );
    }
}
