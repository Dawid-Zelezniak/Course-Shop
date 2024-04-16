package com.zelezniak.project.order;

import com.zelezniak.project.author.CourseAuthor;
import com.zelezniak.project.course.Course;
import com.zelezniak.project.student.Student;

import java.math.BigDecimal;
import java.util.Set;

public interface OrderService {

    static BigDecimal totalOrdersPrice(Set<Order> orders) {
        return orders.stream()
                .map(order -> order.getCoursePrice().getMoney())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    Order createOrder(Course course, CourseAuthor author);

    Order createOrder(Course course, Student student);
}
