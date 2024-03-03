package com.zelezniak.project.service;


import com.zelezniak.project.entity.CourseAuthor;
import com.zelezniak.project.repository.CourseAuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final CourseAuthorRepository authorRepository;

    public CourseAuthor findByEmail(String email) {
        return authorRepository.findByEmail(email);
    }

    public List<CourseAuthor> getAllAuthors() {
        List<CourseAuthor> authors = authorRepository.findAll();
        //return only authors without admin people
        authors.removeIf(courseAuthor -> courseAuthor.getRoles()
                .stream()
                .anyMatch(role -> role.toString().contains("ADMIN")));
        return authors;
    }
}