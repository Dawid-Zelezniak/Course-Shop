package com.zelezniak.project.service;

import com.zelezniak.project.entity.Course;
import com.zelezniak.project.entity.CourseAuthor;
import com.zelezniak.project.entity.Order;
import com.zelezniak.project.entity.Student;

public interface OrderService {

    Order createOrder(Course course, CourseAuthor author);

    Order createOrder(Course course, Student student);
}
