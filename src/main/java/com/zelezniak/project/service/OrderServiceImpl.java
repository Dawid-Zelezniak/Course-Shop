package com.zelezniak.project.service;

import com.zelezniak.project.entity.Course;
import com.zelezniak.project.entity.CourseAuthor;
import com.zelezniak.project.entity.Order;
import com.zelezniak.project.entity.Student;
import com.zelezniak.project.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Order createOrder(Course course, CourseAuthor author) {
        Order order = Order.builder()
                .orderId(generateTrackingNumber())
                .totalPrice(course.getPrice())
                .orderedCourse(course)
                .build();
        order.addUser(author);
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order createOrder(Course course, Student student) {
        Order order = Order.builder()
                .orderId(generateTrackingNumber())
                .totalPrice(course.getPrice())
                .orderedCourse(course)
                .build();
        order.addUser(student);
        orderRepository.save(order);
        return order;
    }

    private String generateTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}
