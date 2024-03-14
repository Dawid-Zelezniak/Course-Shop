

package com.zelezniak.project.order;

import com.zelezniak.project.author.CourseAuthor;
import com.zelezniak.project.course.Course;
import com.zelezniak.project.student.Student;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public final class Order {

    @Id
    @Column(name = "order_id")
    private String orderId;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course orderedCourse;

    @ManyToMany
    @JoinTable(name = "students_orders",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> students;

    @ManyToMany
    @JoinTable(name = "authors_orders",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<CourseAuthor> courseAuthors;

    public void addUser(Student student) {
        if (student != null) {
            if (students == null) {students = new HashSet<>();}
            students.add(student);
        }
    }

    public void addUser(CourseAuthor courseAuthor) {
        if (courseAuthor != null) {
            if (courseAuthors == null) {courseAuthors = new HashSet<>();}
            courseAuthors.add(courseAuthor);
        }
    }

    public static final class OrderBuilder {

        public static Order buildOrder(Course course, CourseAuthor author) {
            Order order = Order.builder()
                    .orderId(generateOrderId())
                    .totalPrice(course.getPrice())
                    .orderedCourse(course)
                    .dateCreated(LocalDateTime.now())
                    .build();
            order.addUser(author);
            return order;
        }

        public static Order buildOrder(Course course, Student student) {
            Order order = Order.builder()
                    .orderId(generateOrderId())
                    .totalPrice(course.getPrice())
                    .orderedCourse(course)
                    .dateCreated(LocalDateTime.now())
                    .build();
            order.addUser(student);
            return order;
        }

        private static String generateOrderId() {
            return UUID.randomUUID().toString();
        }
    }
}
