package com.example.scheduleapi.common.exception;

public class AuthorNotFoundException extends RuntimeException{

    // 존재하지 않는 작성자 대한 예외처리
    public AuthorNotFoundException() {
        super("작성자 조회에 실패했습니다.");
    }
}
