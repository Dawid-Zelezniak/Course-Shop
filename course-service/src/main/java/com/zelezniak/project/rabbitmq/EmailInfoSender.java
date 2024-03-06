package com.zelezniak.project.rabbitmq;

import com.zelezniak.project.course.Course;
import com.zelezniak.project.author.CourseAuthor;
import com.zelezniak.project.student.Student;

public interface EmailInfoSender {

    void prepareEmailInfo(Course course, CourseAuthor author, String orderId);

    void prepareEmailInfo(Course course, Student student,String orderId);
}
