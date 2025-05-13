package com.example.scheduleapi.common.exception;

public class NoRowsAffectedException extends RuntimeException{

    // 수정이나 삭제를 시도했음에도 바뀐 열이 없는 경우 예외처리
    public NoRowsAffectedException(String operation) {
        super(operation + "에 실패했습니다. 변경된 데이터가 없습니다.");
    }
}
