package com.zelezniak.project.service;

import com.zelezniak.project.entity.Course;
import com.zelezniak.project.entity.CourseAuthor;
import com.zelezniak.project.entity.Order;
import com.zelezniak.project.entity.Student;

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
