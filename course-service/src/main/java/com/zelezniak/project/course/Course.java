package com.zelezniak.project.course;

import com.zelezniak.project.author.CourseAuthor;
import com.zelezniak.project.order.Order;
import com.zelezniak.project.student.Student;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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
public class Course {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "price")
    @Min(value = 0L, message = "Price can not be lower than 0")
    private Double price;

    @NotBlank(message = "Title can not be blank")
    @Column(name = "title", unique = true)
    private String title;

    @NotBlank(message = "Description can not be blank")
    @Column(name = "description")
    private String description;

    @ManyToOne
    private CourseAuthor courseAuthor;

    @NotBlank(message = "Category can not be blank")
    @Column(name = "category")
    private String category;

    @Column(name = "date_created")
    @CreationTimestamp
    private LocalDateTime dateCreated;

    @ManyToMany
    @JoinTable(name = "enrolled_students",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> enrolledStudents;

    @ManyToMany
    @JoinTable(name = "enrolled_authors",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<CourseAuthor> enrolledAuthors;

    @OneToMany(mappedBy = "orderedCourse", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Order> orders;


    public void addUserToCourse(Student student) {
        if (student != null) {
            if (enrolledStudents == null) {
                enrolledStudents = new HashSet<>();
            }
            enrolledStudents.add(student);
        }
    }

    public void addUserToCourse(CourseAuthor courseAuthor) {
        if (courseAuthor != null) {
            if (enrolledAuthors == null) {
                enrolledAuthors = new HashSet<>();
            }
            enrolledAuthors.add(courseAuthor);
        }
    }

    public void addOrder(Order order) {
        if (order != null) {
            if (orders == null) {orders = new HashSet<>();}
            orders.add(order);
            order.setOrderedCourse(this);
        }
    }


    public int countTotalParticipants() {
        return enrolledAuthors.size() + enrolledStudents.size();
    }

    public boolean equals(Object o) {
        if (this == o) {return true;}
        else if (o != null && this.getClass() == o.getClass()) {
            Course course = (Course) o;
            return Double.compare(course.price, this.price) == 0 &&
                    Objects.equals(this.courseId, course.courseId) &&
                    Objects.equals(this.title, course.title) &&
                    Objects.equals(this.description, course.description) &&
                    Objects.equals(this.courseAuthor, course.courseAuthor) &&
                    Objects.equals(this.category, course.category) &&
                    Objects.equals(this.dateCreated, course.dateCreated);
        } else {return false;}
    }

    public int hashCode() {
        return Objects.hash(this.courseId,
                this.price, this.title,
                this.description,
                this.courseAuthor,
                this.category,
                this.dateCreated);
    }
}