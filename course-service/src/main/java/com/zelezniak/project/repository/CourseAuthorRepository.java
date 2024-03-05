package com.zelezniak.project.repository;


import com.zelezniak.project.entity.CourseAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseAuthorRepository extends JpaRepository<CourseAuthor, Long> {

    boolean existsByEmail(String email);

    CourseAuthor findByEmail(String email);

    Optional<CourseAuthor> findByAuthorId(Long id);
}
