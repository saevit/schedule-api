package com.example.scheduleapi.dto;

import com.example.scheduleapi.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long id;
    private String task;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Schedle class를 매개변수로 받는 생성자
    public ScheduleResponseDto (Schedule schedule, String authorName) {
        this.id = schedule.getId();
        this.task = schedule.getTask();
        this.author = authorName;
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
    }
}
