package com.zelezniak.project.student;

import com.zelezniak.project.course.Course;
import com.zelezniak.project.order.Order;
import com.zelezniak.project.role.Role;
import com.zelezniak.project.user.UserData;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "students")
public final class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "date_created")
    @CreationTimestamp
    private LocalDateTime dateCreated;

    @ManyToMany(mappedBy = "enrolledStudents")
    private Set<Course> boughtCourses;

    @ManyToMany(mappedBy = "students")
    private Set<Order> studentOrders;

    @ManyToOne
    private Role role;

    public void addOrder(Order order) {
        if (order != null) {
            if (studentOrders == null) {studentOrders = new HashSet<>();}
            studentOrders.add(order);
        }
    }

    public void addBoughtCourse(Course course) {
        if (course != null) {
            if (boughtCourses == null) {boughtCourses = new HashSet<>();}
            boughtCourses.add(course);
        }
    }

    public static final class StudentBuilder {

        public static Student buildStudent(UserData user, BCryptPasswordEncoder passwordEncoder) {
            return Student.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .build();
        }
    }
}