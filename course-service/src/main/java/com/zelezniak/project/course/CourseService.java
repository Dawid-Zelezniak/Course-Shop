package com.zelezniak.project.course;


import com.zelezniak.project.dto.PaymentInfo;

import java.security.Principal;
import java.util.List;

public interface CourseService {

    void addCourse(Course course, Long authorId);

    Course findById(Long courseId);

    void updateCourse(Long courseId, Course course);

    List<Course> getAllAvailableCourses(String name);

    void addBoughtCourseAndOrderForUser(String email, String courseName);

    PaymentInfo prepareOrderInfo(Long courseId, Principal principal);

}