package com.zelezniak.project.course;

import com.zelezniak.project.author.CourseAuthor;
import com.zelezniak.project.order.Order;
import com.zelezniak.project.student.Student;
import com.zelezniak.project.valueobjects.Money;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "courses")
public final class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @Embedded
    private Money price;

    @NotBlank(message = "Title can not be blank")
    @Column(name = "title", unique = true)
    private String title;

    @NotBlank(message = "Description can not be blank")
    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private CourseAuthor courseAuthor;

    @NotBlank(message = "Category can not be blank")
    @Column(name = "category")
    private String category;

    @Column(name = "date_created")
    @CreationTimestamp
    private LocalDateTime dateCreated;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    @JoinTable(name = "enrolled_students",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> enrolledStudents;

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    @JoinTable(name = "enrolled_authors",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<CourseAuthor> enrolledAuthors;

    @OneToMany(mappedBy = "orderedCourse",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST,CascadeType.DETACH})
    private Set<Order> orders;

    public void addOrder(Order order) {
        if (order != null) {
            if (orders == null) {orders = new HashSet<>();}
            orders.add(order);
            order.setOrderedCourse(this);
        }
    }

    public int countCourseParticipants() {
        return enrolledAuthors.size() + enrolledStudents.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(courseId, course.courseId) &&
                Objects.equals(price, course.price) && Objects.equals(title, course.title) &&
                Objects.equals(description, course.description) && Objects.equals(courseAuthor, course.courseAuthor) &&
                Objects.equals(category, course.category) && Objects.equals(dateCreated, course.dateCreated);
    }

    public int hashCode() {
        return Objects.hash(this.courseId,
                this.price,
                this.title,
                this.description,
                this.courseAuthor,
                this.category,
                this.dateCreated);
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", courseAuthor=" + courseAuthor +
                ", dateCreated=" + dateCreated +
                ", category='" + category + '\'' +
                '}';
    }
}