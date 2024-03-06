package com.zelezniak.project.author;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseAuthorRepository extends JpaRepository<CourseAuthor, Long> {

    boolean existsByEmail(String email);

    CourseAuthor findByEmail(String email);

    Optional<CourseAuthor> findByAuthorId(Long id);
}
