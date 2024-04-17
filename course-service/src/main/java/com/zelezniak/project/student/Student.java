package com.zelezniak.project.student;

import com.zelezniak.project.course.Course;
import com.zelezniak.project.order.Order;
import com.zelezniak.project.role.Role;
import com.zelezniak.project.user.UserData;
import com.zelezniak.project.valueobjects.UserCredentials;
import com.zelezniak.project.valueobjects.UserName;
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

    @Embedded
    private UserName userName;

    @Embedded
    private UserCredentials userCredentials;

    @Column(name = "date_created")
    @CreationTimestamp
    private LocalDateTime dateCreated;

    @ManyToMany(mappedBy = "enrolledStudents",
            cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    private Set<Course> boughtCourses;

    @ManyToMany(mappedBy = "students",cascade = CascadeType.PERSIST)
    private Set<Order> studentOrders;

    @ManyToOne
    private Role role;

    public void addOrder(Order order) {
        if (order != null) {
            if (studentOrders == null) {
                studentOrders = new HashSet<>();
            }
            studentOrders.add(order);
        }
    }

    public void addBoughtCourse(Course course) {
        if (course != null) {
            if (boughtCourses == null) {
                boughtCourses = new HashSet<>();
            }
            boughtCourses.add(course);
            course.getEnrolledStudents().add(this);
        }
    }

    public static final class StudentBuilder {

        public static Student buildStudent(UserData user, BCryptPasswordEncoder passwordEncoder) {
            return Student.builder()
                    .userName(new UserName(user.getFirstName(), user.getLastName()))
                    .userCredentials(new UserCredentials(user.getEmail(), passwordEncoder.encode(user.getPassword())))
                    .build();
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", firstName='" + userName.getFirstName() + '\'' +
                ", lastName='" + userName.getLastName() + '\'' +
                ", email='" + userCredentials.getEmail() + '\'' +
                ", dateCreated=" + dateCreated +
                ", role=" + role +
                '}';
    }
}