package com.zelezniak.project.dto;

import com.zelezniak.project.author.CourseAuthor;
import com.zelezniak.project.course.Course;
import com.zelezniak.project.student.Student;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public final class EmailInfo implements Serializable {

    private String email;
    private String firstName;
    private String lastName;
    private String title;
    private Double price;
    private String orderId;

    public static final class EmailInfoBuilder{

        public static EmailInfo buildEmailInfo(Course course, CourseAuthor author, String orderId) {
            return EmailInfo.builder()
                    .email(author.getEmail())
                    .title(course.getTitle())
                    .price(course.getPrice())
                    .firstName(author.getFirstName())
                    .lastName(author.getLastName())
                    .orderId(orderId)
                    .build();
        }

        public static EmailInfo buildEmailInfo(Course course, Student student, String orderId) {
            return EmailInfo.builder()
                    .email(student.getEmail())
                    .title(course.getTitle())
                    .price(course.getPrice())
                    .firstName(student.getFirstName())
                    .lastName(student.getLastName())
                    .orderId(orderId)
                    .build();
        }
    }
}
