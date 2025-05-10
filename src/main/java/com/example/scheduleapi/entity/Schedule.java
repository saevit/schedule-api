package com.example.scheduleapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {
    @Setter
    private Long id;
    private String task;
    private String author;
    private String password;
    @Setter
    private LocalDateTime createdAt;
    @Setter
    private LocalDateTime updatedAt;

    public Schedule (String task, String author, String password) {
        this.task = task;
        this.author = author;
        this.password = password;
    }

}
