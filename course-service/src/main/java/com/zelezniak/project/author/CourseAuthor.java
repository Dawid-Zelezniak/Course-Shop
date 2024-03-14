package com.zelezniak.project.author;

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
import java.util.Objects;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name = "course_authors")
public final class CourseAuthor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long authorId;

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

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "created_by_author",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> createdByAuthor;

    @ManyToMany(mappedBy = "enrolledAuthors")
    private Set<Course> boughtCourses;

    @ManyToMany(mappedBy = "courseAuthors")
    private Set<Order> authorOrders;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "authors_roles",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public void addAuthorCourse(Course course) {
        if (course != null) {
            if (createdByAuthor == null) {createdByAuthor = new HashSet<>();}
            createdByAuthor.add(course);
        }
    }

    public void addBoughtCourse(Course course) {
        if (course != null) {
            if (boughtCourses == null) {boughtCourses = new HashSet<>();}
            boughtCourses.add(course);
        }
    }

    public void addOrder(Order order) {
        if (order != null) {
            if (authorOrders == null) {authorOrders = new HashSet<>();}
            authorOrders.add(order);
        }
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public boolean equals(Object o) {
        if (this == o) {return true;}
        else if (o != null && this.getClass() == o.getClass()) {
            CourseAuthor that = (CourseAuthor) o;
            return Objects.equals(this.authorId, that.authorId) &&
                    Objects.equals(this.firstName, that.firstName) &&
                    Objects.equals(this.lastName, that.lastName) &&
                    Objects.equals(this.email, that.email) &&
                    Objects.equals(this.dateCreated, that.dateCreated);
        } else {return false;}
    }

    public int hashCode() {
        return Objects.hash(this.authorId, this.firstName,
                this.lastName, this.email, this.dateCreated);
    }

    public static final class CourseAuthorBuilder{

        public static CourseAuthor buildAuthor(UserData user, BCryptPasswordEncoder passwordEncoder) {
            return CourseAuthor.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .build();
        }
    }
}