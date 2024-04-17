package com.zelezniak.project.course;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    boolean existsByTitle(String title);

    Optional<Course> findByTitle(String title);

}
    