package com.example.scheduleapi.repository;

import com.example.scheduleapi.entity.Author;

import java.util.Optional;

public interface AuthorRepository {

    Author saveAuthor(Author author);

    Author findAuthorById(Long authorId);

    Optional<Author> findAuthorByNameAndEmail(String name, String email);
}
