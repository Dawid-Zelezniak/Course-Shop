package com.zelezniak.project.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CourseRepository extends JpaRepository<Course, Long> {

    boolean existsByTitle(String title);

    Optional<Course> findByTitle(String title);

    @Query("SELECT c FROM Course c " +
            "LEFT JOIN c.enrolledAuthors ea " +
            "WHERE (ea.authorId IS NULL OR ea.authorId <> :authorId) " +
            "AND ( c.courseAuthor.authorId <> :authorId)")
    List<Course> findAllAvailableCoursesForAuthor(@Param("authorId") Long authorId);


    @Query("SELECT c FROM Course c " +
            "WHERE c NOT IN :boughtCourses")
    List<Course> findAllAvailableCoursesForStudent(@Param("boughtCourses") Set<Course> boughtCourses);

}
    