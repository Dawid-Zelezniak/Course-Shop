package com.zelezniak.project.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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
public class Student {

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "students_roles",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

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
}