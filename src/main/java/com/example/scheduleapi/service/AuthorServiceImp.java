package com.example.scheduleapi.service;

import com.example.scheduleapi.entity.Author;
import com.example.scheduleapi.repository.AuthorRepository;
import com.example.scheduleapi.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorServiceImp implements AuthorService{

    private final AuthorRepository authorRepository;

    public AuthorServiceImp(ScheduleRepository scheduleRepository, AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author findOrSaveAuthor(String name, String email) {
        // 요청받은 이름과 이메일이 동일한 작성자가 존재하는지 조회
        Optional<Author> optionalAuthor = authorRepository.findAuthorByNameAndEmail(name, email);

        // 존재하지 않는다면 Author 생성하여 author 겟
        // 존재한다면 기존의 author
        Author author;
        if (optionalAuthor.isEmpty()){
            author = new Author(name, email);
            author = authorRepository.saveAuthor(author);
        } else {
            author = optionalAuthor.get();
        }

        return author;
    }
}
