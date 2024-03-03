package com.zelezniak.project.repository;

import com.zelezniak.project.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByTitle(String title);

    Optional<Course> findByTitle(String title);
}
    