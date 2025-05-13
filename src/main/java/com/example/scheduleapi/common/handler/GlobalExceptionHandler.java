package com.example.scheduleapi.common.handler;

import com.example.scheduleapi.common.dto.ErrorResponseDto;
import com.example.scheduleapi.common.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 잘못된 비밀번호 예외처리
    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ErrorResponseDto> handleIncorrectPassword(IncorrectPasswordException e) {
        // 오류 응답용
        ErrorResponseDto responseDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(),e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

    // 존재하지 않는 일정에 대한 예외처리
    @ExceptionHandler(ScheduleNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleScheduleNotFound(ScheduleNotFoundException e) {
        // 오류 응답용
        ErrorResponseDto responseDto = new ErrorResponseDto(HttpStatus.NOT_FOUND.value(),e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
    }

    // 존재하지 않는 작성자 대한 예외처리
    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleAuthorNotFound(AuthorNotFoundException e) {
        // 오류 응답용
        ErrorResponseDto responseDto = new ErrorResponseDto(HttpStatus.NOT_FOUND.value(),e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
    }

    // 페이지에 일정이 존재하지 않는 경우에 대한 예외처리
    @ExceptionHandler(SchedulePageNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleSchedulePageNotFound(SchedulePageNotFoundException e) {
        // 오류 응답용
        ErrorResponseDto responseDto = new ErrorResponseDto(HttpStatus.NOT_FOUND.value(),e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
    }

    // 수정이나 삭제를 시도했음에도 바뀐 열이 없는 경우 예외처리
    @ExceptionHandler(NoRowsAffectedException.class)
    public ResponseEntity<ErrorResponseDto> handleNoRowsAffected(NoRowsAffectedException e) {
        // 오류 응답용
        ErrorResponseDto responseDto = new ErrorResponseDto(HttpStatus.NOT_FOUND.value(),e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
    }
}
