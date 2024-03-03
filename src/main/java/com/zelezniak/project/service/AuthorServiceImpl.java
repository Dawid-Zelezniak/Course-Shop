package com.zelezniak.project.service;


import com.zelezniak.project.entity.CourseAuthor;
import com.zelezniak.project.exception.CourseException;
import com.zelezniak.project.exception.CustomErrors;
import com.zelezniak.project.repository.CourseAuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements com.zelezniak.project.service.AuthorService {
    private final CourseAuthorRepository authorRepository;

    public CourseAuthor findByEmail(String email) {
        return authorRepository.findByEmail(email);
    }

    public List<CourseAuthor> getAllAuthors() {
        List<CourseAuthor> all = authorRepository.findAll();
        all.removeIf(courseAuthor -> courseAuthor.getRoles()
                .stream()
                .anyMatch(role -> role.toString().contains("ADMIN")));
        return all;
    }
}