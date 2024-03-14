package com.zelezniak.project.order;

import com.zelezniak.project.course.Course;
import com.zelezniak.project.author.CourseAuthor;
import com.zelezniak.project.student.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
final class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public Order createOrder(Course course, CourseAuthor author) {
        Order order = Order
                .OrderBuilder
                .buildOrder(course,author);
        orderRepository.save(order);
        return order;
    }

    @Override
    public Order createOrder(Course course, Student student) {
        Order order = Order
                .OrderBuilder
                .buildOrder(course,student);
        orderRepository.save(order);
        return order;
    }
}
