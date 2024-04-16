package com.zelezniak.project.author;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseAuthorRepository extends JpaRepository<CourseAuthor, Long> {

    boolean existsByUserCredentialsEmail(String email);

    CourseAuthor findByUserCredentialsEmail(String email);

    Optional<CourseAuthor> findByAuthorId(Long id);
}
