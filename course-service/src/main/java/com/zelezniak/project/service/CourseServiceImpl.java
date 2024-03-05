package com.zelezniak.project.service;


import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.zelezniak.project.entity.Course;
import com.zelezniak.project.entity.CourseAuthor;
import com.zelezniak.project.entity.Order;
import com.zelezniak.project.entity.Student;
import com.zelezniak.project.exception.CourseException;
import com.zelezniak.project.exception.CustomErrors;
import com.zelezniak.project.rabbitmq.EmailInfoSender;
import com.zelezniak.project.repository.CourseAuthorRepository;
import com.zelezniak.project.repository.CourseRepository;
import com.zelezniak.project.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final AuthorService authorService;
    private final StudentService studentService;
    private final OrderService orderService;
    private final EmailInfoSender emailInfoPublisher;

    @Transactional
    public void addCourse(Course course, Long authorId) {
        if (course != null) {
            checkIfCourseExists(course);
            CourseAuthor authorFromDb = authorService.getById(authorId);
            authorFromDb.addAuthorCourse(course);
            courseRepository.save(course);
            authorService.saveAuthor(authorFromDb);
            course.setCourseAuthor(authorFromDb);
        }
    }

    public Course findById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() ->new CourseException(CustomErrors.COURSE_NOT_FOUND));
    }

    @Transactional
    public void updateCourse(Long courseId, Course course) {
        Course courseFromDb = findById(courseId);
        //check if course exists in database
        if (!courseFromDb.getTitle().equals(course.getTitle())
                && courseRepository.existsByTitle(course.getTitle())) {
            throw new CourseException(CustomErrors.COURSE_ALREADY_EXISTS);}
        else {
            setCourse(courseFromDb, course);
            courseRepository.save(courseFromDb);
        }
    }

    public List<Course> getAllAvailableCourses(String userEmail) {
        List<Course> coursesFromDb = courseRepository.findAll();
        CourseAuthor courseAuthor = authorService.findByEmail(userEmail);
        Student student = studentService.findByEmail(userEmail);
        return courseAuthor != null ?
                coursesAvailableForAuthor(coursesFromDb, courseAuthor) :
                coursesAvailableForStudent(coursesFromDb, student);
    }

    @Transactional
    public void addBoughtCourseAndOrderForUser(String email, String productName) {
        CourseAuthor author = authorService.findByEmail(email);
        Student student = studentService.findByEmail(email);
        Course course = courseRepository.findByTitle(productName)
                .orElseThrow(() -> new CourseException(CustomErrors.COURSE_NOT_FOUND));
        if (author != null) {addCourseAndOrder(course, author);}
        else {addCourseAndOrder(course, student);}
    }

    private void addCourseAndOrder(Course course, CourseAuthor author) {
        Order order = orderService.createOrder(course, author);
        emailInfoPublisher.prepareEmailInfo(course, author,order.getOrderId());
        course.addOrder(order);
        author.addOrder(order);
        addCourseForAuthor(author, course);
    }

    private void addCourseAndOrder(Course course, Student student) {
        Order order = orderService.createOrder(course, student);
        emailInfoPublisher.prepareEmailInfo(course,student,order.getOrderId());
        course.addOrder(order);
        student.addOrder(order);
        addCourseForStudent(student, course);
    }

    private void addCourseForStudent(Student student, Course course) {
        student.addBoughtCourse(course);
        course.addUserToCourse(student);
        studentService.saveStudent(student);
        courseRepository.save(course);
    }

    private void addCourseForAuthor(CourseAuthor author, Course course) {
        author.addBoughtCourse(course);
        course.addUserToCourse(author);
        authorService.saveAuthor(author);
        courseRepository.save(course);
    }

    private List<Course> coursesAvailableForStudent(List<Course> coursesFromDb, Student student) {
        Set<Course> boughtCourses = student.getBoughtCourses();
        return deleteCoursesFromList(coursesFromDb, boughtCourses);
    }

    private List<Course> coursesAvailableForAuthor(List<Course> coursesFromDb, CourseAuthor courseAuthor) {
        Set<Course> createdByAuthor = courseAuthor.getCreatedByAuthor();
        Set<Course> boughtCourses = courseAuthor.getBoughtCourses();
        Set<Course> coursesToDelete = Stream.of(createdByAuthor, boughtCourses)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        return deleteCoursesFromList(coursesFromDb, coursesToDelete);
    }

    private static List<Course> deleteCoursesFromList(List<Course> coursesFromDb, Set<Course> coursesToDelete) {
        coursesFromDb.removeAll(coursesToDelete);
        return coursesFromDb;
    }

    private void setCourse(Course courseFromDb, Course course) {
        courseFromDb.setTitle(course.getTitle());
        courseFromDb.setDescription(course.getDescription());
        courseFromDb.setCategory(course.getCategory());
        courseFromDb.setPrice(course.getPrice());
    }

    private void checkIfCourseExists(Course course) {
        if (courseRepository.existsByTitle(course.getTitle())) {
            throw new CourseException(CustomErrors.COURSE_ALREADY_EXISTS);
        }
    }
}