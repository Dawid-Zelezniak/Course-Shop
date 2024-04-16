package com.zelezniak.project.dto;

import com.zelezniak.project.author.CourseAuthor;
import com.zelezniak.project.course.Course;
import com.zelezniak.project.student.Student;
import com.zelezniak.project.valueobjects.Money;
import com.zelezniak.project.valueobjects.UserName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public final class EmailInfo implements Serializable {

    private String email;
    private UserName userName;
    private String title;
    private Money price;
    private String orderId;

    public static final class EmailInfoBuilder {

        public static EmailInfo buildEmailInfo(Course course, CourseAuthor author, String orderId) {
            return EmailInfo.builder()
                    .email(author.getUserCredentials().getEmail())
                    .title(course.getTitle())
                    .price(course.getPrice())
                    .userName(author.getUserName())
                    .orderId(orderId)
                    .build();
        }

        public static EmailInfo buildEmailInfo(Course course, Student student, String orderId) {
            return EmailInfo.builder()
                    .email(student.getUserCredentials().getEmail())
                    .title(course.getTitle())
                    .price(course.getPrice())
                    .userName(student.getUserName())
                    .orderId(orderId)
                    .build();
        }
    }
}
