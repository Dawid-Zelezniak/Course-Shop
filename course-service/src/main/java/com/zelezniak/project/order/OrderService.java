package com.zelezniak.project.order;

import com.zelezniak.project.author.CourseAuthor;
import com.zelezniak.project.course.Course;
import com.zelezniak.project.student.Student;

import java.util.Set;

public interface OrderService {

    static double totalOrdersPrice(Set<Order> orders) {
        return orders.stream()
                .map(Order::getTotalPrice)
                .reduce(0.0, Double::sum);
    }

    Order createOrder(Course course, CourseAuthor author);

    Order createOrder(Course course, Student student);
}
