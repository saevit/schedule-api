package com.example.scheduleapi.common.exception;

public class SchedulePageNotFoundException extends RuntimeException {

    // 페이지에 일정이 존재하지 않는 경우에 대한 예외처리
    public SchedulePageNotFoundException() {
        super("존재하지 않는 페이지 입니다.");
    }
}
