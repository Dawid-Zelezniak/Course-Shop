

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
@Table(name = "orders")
public class Order {

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

}
