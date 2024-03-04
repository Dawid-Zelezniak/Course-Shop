package com.zelezniak.project.rabbitmq;

import com.zelezniak.project.entity.Course;
import com.zelezniak.project.entity.CourseAuthor;
import com.zelezniak.project.entity.Student;

public interface EmailInfoSender {

    void prepareEmailInfo(Course course, CourseAuthor author, String orderId);

    void prepareEmailInfo(Course course, Student student,String orderId);
}
