package com.example.scheduleapi.controller;

import com.example.scheduleapi.dto.ScheduleRequestDto;
import com.example.scheduleapi.dto.ScheduleResponseDto;
import com.example.scheduleapi.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/schdeule") // prefix
public class ScheduleController {

    // DB 역할 수행을 위해... (임시)
    private final Map<Long, Schedule> scheduleList = new HashMap<>();

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto requestDto) {

        // 현재 ID를 구하기 위해 스케쥴 중 가장 큰 키 값 구하여 +1
        Long Id = scheduleList.isEmpty() ? 1 : Collections.max(scheduleList.keySet()) + 1;

        // 현재 날짜 및 시간 구하기
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        // 요청받은 데이터로 Schedule 객체 생성
        Schedule schedule = new Schedule(Id, requestDto.getTask(), requestDto.getAuthor(), requestDto.getPassword(), createdAt, updatedAt);

        // 임시 DB에 일정 저장
        scheduleList.put(Id, schedule);

        return new ResponseEntity<>(new ScheduleResponseDto(schedule), HttpStatus.CREATED);
    }

}
