package com.example.scheduleapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class Schedule {
    private Long id;
    private String task;
    private Long authorId;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Schedule (String task, Long authorId, String password) {
        this.task = task;
        this.authorId = authorId;
        this.password = password;
    }
}
