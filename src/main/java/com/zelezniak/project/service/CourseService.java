package com.zelezniak.project.service;


import com.zelezniak.project.entity.Course;
import com.zelezniak.project.entity.CourseAuthor;

import java.util.List;

public interface CourseService {

    void addCourse(Course course, Long authorId);

    void deleteCourse(Long courseId, CourseAuthor authorFromDb);

    Course getById(Long courseId);

    void updateCourse(Long courseId, Course course);

    List<Course> getAllAvailableCourses(String name);
}