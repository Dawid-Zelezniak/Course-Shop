package com.zelezniak.project.rabbitmq;

import com.zelezniak.project.course.Course;
import com.zelezniak.project.author.CourseAuthor;
import com.zelezniak.project.student.Student;

public interface EmailPublisherService {

    void prepareAndSendEmailInfo(Course course, CourseAuthor author, String orderId);

    void prepareAndSendEmailInfo(Course course, Student student, String orderId);
}
