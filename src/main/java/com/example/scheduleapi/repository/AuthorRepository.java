package com.example.scheduleapi.repository;

import com.example.scheduleapi.entity.Author;

public interface AuthorRepository {

    Author saveAuthor(Author author);

    Author findAuthorById(Long authorId);

    Author findOrSaveAuthor (String name, String email);
}
