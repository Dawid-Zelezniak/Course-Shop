package com.zelezniak.project.author;


import java.util.List;

public interface AuthorService {

    CourseAuthor findByEmail(String email);

    List<CourseAuthor> getAllAuthors();

    CourseAuthor findById(Long authorId);

    void saveAuthor(CourseAuthor author);

    boolean existsByEmail(String email);
}