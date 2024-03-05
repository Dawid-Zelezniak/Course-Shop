package com.zelezniak.project.service;


import com.zelezniak.project.entity.CourseAuthor;

import java.util.List;

public interface AuthorService {

    CourseAuthor findByEmail(String email);

    List<CourseAuthor> getAllAuthors();

    CourseAuthor getById(Long authorId);

    void saveAuthor(CourseAuthor author);

}