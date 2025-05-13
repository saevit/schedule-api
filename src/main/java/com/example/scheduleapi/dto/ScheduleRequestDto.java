package com.example.scheduleapi.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    private String task;
    private String name;
    private String email;
    private String password;
}
