package com.example.scheduleapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class Author {
    private Long authorId;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Author (String name, String email) {
        this.name = name;
        this.email = email;
    }
}
