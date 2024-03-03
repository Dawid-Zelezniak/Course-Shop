package com.zelezniak.project.service;


import com.zelezniak.project.entity.Course;
import com.zelezniak.project.entity.CourseAuthor;

import java.util.List;

public interface CourseService {

    void addCourse(Course course, Long authorId);

    Course getById(Long courseId);

    void updateCourse(Long courseId, Course course);

    List<Course> getAllAvailableCourses(String name);

    void addBoughtCourseAndOrderForUser(String email, String productName);
}