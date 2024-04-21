package com.zelezniak.project.rabbitmq;

import com.zelezniak.project.dto.EmailInfo;
import com.zelezniak.project.course.Course;
import com.zelezniak.project.author.CourseAuthor;
import com.zelezniak.project.student.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
final class EmailInfoPublisherImpl implements EmailPublisherService {

    private final RabbitTemplate rabbitTemplate;

    private static final String QUEUE_NAME = "emails-queue";

    public void prepareAndSendEmailInfo(Course course, CourseAuthor author, String orderId) {
        EmailInfo info = EmailInfo
                .EmailInfoBuilder
                .buildEmailInfo(course,author,orderId);
        publishEmailInfo(info);
    }

    public void prepareAndSendEmailInfo(Course course, Student student, String orderId) {
        EmailInfo info = EmailInfo
                .EmailInfoBuilder
                .buildEmailInfo(course,student,orderId);
        publishEmailInfo(info);
    }

    private void publishEmailInfo(EmailInfo emailInfo) {
        rabbitTemplate.convertAndSend(QUEUE_NAME, emailInfo);
    }
}
