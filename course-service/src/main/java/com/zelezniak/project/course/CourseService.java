package com.zelezniak.project.course;


import java.util.List;

public interface CourseService {

    void addCourse(Course course, Long authorId);

    Course findById(Long courseId);

    void updateCourse(Long courseId, Course course);

    List<Course> getAllAvailableCourses(String name);

    void addBoughtCourseAndOrderForUser(String email, String productName);

}