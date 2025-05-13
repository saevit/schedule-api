package com.example.scheduleapi.common.exception;

public class ScheduleNotFoundException extends RuntimeException{

    // 존재하지 않는 일정에 대한 예외처리
    public ScheduleNotFoundException() {
        super("존재하지 않는 일정입니다.");
    }
}
