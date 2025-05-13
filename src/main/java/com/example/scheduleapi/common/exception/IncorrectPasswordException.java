package com.example.scheduleapi.common.exception;

public class IncorrectPasswordException extends RuntimeException{

    public IncorrectPasswordException() {
        super("Password is incorrect.");
    }
}
