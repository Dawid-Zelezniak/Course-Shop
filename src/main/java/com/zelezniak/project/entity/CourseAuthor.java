package com.zelezniak.project.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "course_authors")
public class CourseAuthor {
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
    private LocalDateTime dateCreated = LocalDateTime.now();

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "created_by_author",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> createdByAuthor;

    @ManyToMany(mappedBy = "enrolledAuthors")
    private Set<Course> boughtCourses;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "authors_orders",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private Set<Order> authorOrders;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "authors_roles",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public void addAuthorCourse(Course course) {
        if (course != null) {
            if (this.createdByAuthor == null) {
                this.createdByAuthor = new HashSet<>();
            }
            this.createdByAuthor.add(course);
        }
    }

    public void addBoughtCourse(Course course) {
        if (course != null) {
            if (this.boughtCourses == null) {
                this.boughtCourses = new HashSet<>();
            }
            this.boughtCourses.add(course);
        }
    }

    public void addOrder(Order order) {
        if (order != null) {
            if (this.authorOrders == null) {
                this.authorOrders = new HashSet<>();
            }

            this.authorOrders.add(order);
        }
    }

    public void removeCourseFromCreatedByAuthor(Long courseId) {
        if (this.createdByAuthor != null) {
            this.createdByAuthor.removeIf((course) -> course.getCourseId().equals(courseId));
        }
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            CourseAuthor that = (CourseAuthor) o;
            return Objects.equals(this.authorId, that.authorId) &&
                    Objects.equals(this.firstName, that.firstName) &&
                    Objects.equals(this.lastName, that.lastName) &&
                    Objects.equals(this.email, that.email) &&
                    Objects.equals(this.dateCreated, that.dateCreated);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(this.authorId, this.firstName,
                this.lastName, this.email, this.dateCreated);
    }

}