package com.zelezniak.project.rabbitmq;

import com.zelezniak.project.dto.EmailInfo;
import com.zelezniak.project.entity.Course;
import com.zelezniak.project.entity.CourseAuthor;
import com.zelezniak.project.entity.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailInfoPublisherImpl implements EmailInfoSender {

    private final RabbitTemplate rabbitTemplate;

    private static final String QUEUE_NAME = "emails-queue";

    @Override
    public void prepareEmailInfo(Course course, CourseAuthor author, String orderId) {
        EmailInfo info = EmailInfo.builder()
                .email(author.getEmail())
                .title(course.getTitle())
                .price(course.getPrice())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .orderId(orderId)
                .build();
        publishEmailInfo(info);
        log.info("IIIIIIIIIIIIIIIII"+info);
    }

    @Override
    public void prepareEmailInfo(Course course, Student student,String orderId) {
        EmailInfo info = EmailInfo.builder()
                .email(student.getEmail())
                .title(course.getTitle())
                .price(course.getPrice())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .orderId(orderId)
                .build();
        publishEmailInfo(info);
        log.info("222222222222222222"+info);
    }

    private void publishEmailInfo(EmailInfo emailInfo) {
        rabbitTemplate.convertAndSend(QUEUE_NAME, emailInfo);
    }
}
