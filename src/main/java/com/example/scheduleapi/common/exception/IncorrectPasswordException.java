package com.example.scheduleapi.common.exception;

public class IncorrectPasswordException extends RuntimeException{

    // 잘못된 비밀번호 예외처리
    public IncorrectPasswordException() {
        super("잘못된 비밀번호 입니다.");
    }
}
