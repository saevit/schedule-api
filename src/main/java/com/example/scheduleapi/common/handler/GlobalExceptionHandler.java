package com.example.scheduleapi.common.handler;

import com.example.scheduleapi.common.dto.ErrorResponseDto;
import com.example.scheduleapi.common.exception.IncorrectPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<ErrorResponseDto> handleIncorrectPassword(IncorrectPasswordException e) {
        // 오류 응답용
        ErrorResponseDto responseDto = new ErrorResponseDto(
                HttpStatus.BAD_REQUEST.value(), // 400 상태 코드
                e.getMessage() // 오류 메시지
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
    }

}
