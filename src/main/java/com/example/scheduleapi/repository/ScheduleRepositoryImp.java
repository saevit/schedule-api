package com.example.scheduleapi.repository;

import com.example.scheduleapi.entity.Schedule;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ScheduleRepositoryImp implements ScheduleRepository{

    // DB 역할 수행을 위해... (임시)
    private final Map<Long, Schedule> scheduleList = new HashMap<>();

    @Override
    public Schedule saveschedule(Schedule schedule) {
        // 현재 ID를 구하기 위해 스케쥴 중 가장 큰 키 값 구하여 +1
        Long Id = scheduleList.isEmpty() ? 1 : Collections.max(scheduleList.keySet()) + 1;
        schedule.setId(Id);

        // 현재 날짜 및 시간 구하기
        LocalDateTime createdAt = LocalDateTime.now();
        schedule.setCreatedAt(createdAt);
        LocalDateTime updatedAt = LocalDateTime.now();
        schedule.setUpdatedAt(updatedAt);

        // 임시 DB에 일정 저장
        scheduleList.put(Id, schedule);

        return schedule;
    }
}
