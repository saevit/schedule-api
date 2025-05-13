package com.example.scheduleapi.service;

import com.example.scheduleapi.entity.Author;

public interface AuthorService {

    Author findOrSaveAuthor (String name, String email);
}
