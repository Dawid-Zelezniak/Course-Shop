package com.zelezniak.project.course;


import com.zelezniak.project.author.AuthorService;
import com.zelezniak.project.author.CourseAuthor;
import com.zelezniak.project.dto.PaymentInfo;
import com.zelezniak.project.exception.CourseError;
import com.zelezniak.project.exception.CourseException;
import com.zelezniak.project.order.Order;
import com.zelezniak.project.order.OrderService;
import com.zelezniak.project.rabbitmq.EmailPublisherService;
import com.zelezniak.project.student.Student;
import com.zelezniak.project.student.StudentService;
import com.zelezniak.project.valueobjects.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final AuthorService authorService;
    private final StudentService studentService;
    private final OrderService orderService;
    private final EmailPublisherService emailInfoPublisher;

    @Transactional
    public void addCourse(Course course, Long authorId) {
        if (course != null) {
            checkIfCourseExists(course);
            CourseAuthor authorFromDb = authorService.findById(authorId);
            authorFromDb.addNewCourse(course);
            courseRepository.save(course);
        }
    }

    public Course findById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() ->new CourseException(CourseError.COURSE_NOT_FOUND));
    }

    @Transactional
    public void updateCourse(Long courseId, Course course) {
        Course courseFromDb = findById(courseId);
        //check if course exists in database
        if (!courseFromDb.getTitle().equals(course.getTitle())
                && courseRepository.existsByTitle(course.getTitle())) {
            throw new CourseException(CourseError.COURSE_ALREADY_EXISTS);}
        else {
            setCourse(courseFromDb, course);
            courseRepository.save(courseFromDb);
        }
    }

    public List<Course> getAllAvailableCourses(String userEmail) {
        CourseAuthor courseAuthor = authorService.findByEmail(userEmail);
        Student student = studentService.findByEmail(userEmail);
        return courseAuthor != null ?
                coursesAvailableForAuthor(courseAuthor.getAuthorId()) :
                coursesAvailableForStudent(student);
    }

    @Transactional
    public void addBoughtCourseAndOrderForUser(String email, String productName) {
        CourseAuthor author = authorService.findByEmail(email);
        Student student = studentService.findByEmail(email);
        Course course = courseRepository.findByTitle(productName)
                .orElseThrow(() -> new CourseException(CourseError.COURSE_NOT_FOUND));
        if (author != null) {addCourseAndOrder(course, author);}
        else {addCourseAndOrder(course, student);}
    }

    public PaymentInfo prepareOrderInfo(Long courseId, Principal principal) {
        Course courseToBuy = findById(courseId);
        return PaymentInfo.PaymentInfoBuilder
                .buildPaymentInfo(courseToBuy, principal.getName());
    }

    private void addCourseAndOrder(Course course, CourseAuthor author) {
        Order order = orderService.createOrder(course, author);
        emailInfoPublisher.prepareAndSendEmailInfo(course, author,order.getOrderId());
        course.addOrder(order);
        author.addOrder(order);
        addCourseForAuthor(author, course);
    }

    private void addCourseAndOrder(Course course, Student student) {
        Order order = orderService.createOrder(course, student);
        emailInfoPublisher.prepareAndSendEmailInfo(course,student,order.getOrderId());
        course.addOrder(order);
        student.addOrder(order);
        addCourseForStudent(student, course);
    }

    private void addCourseForStudent(Student student, Course course) {
        student.addBoughtCourse(course);
        studentService.saveStudent(student);
    }

    private void addCourseForAuthor(CourseAuthor author, Course course) {
        author.addBoughtCourse(course);
        authorService.saveAuthor(author);
    }

    private List<Course> coursesAvailableForStudent(Student student) {
        return courseRepository.findAllAvailableCoursesForStudent(student.getBoughtCourses());
    }

    private List<Course> coursesAvailableForAuthor(Long authorId) {
     return courseRepository.findAllAvailableCoursesForAuthor(authorId);
    }

    private void setCourse(Course courseFromDb, Course course) {
        courseFromDb.setTitle(course.getTitle());
        courseFromDb.setDescription(course.getDescription());
        courseFromDb.setCategory(course.getCategory());
        courseFromDb.setPrice(new Money(course.getPrice().getMoney().toString()));
    }

    private void checkIfCourseExists(Course course) {
        if (courseRepository.existsByTitle(course.getTitle())) {
            throw new CourseException(CourseError.COURSE_ALREADY_EXISTS);
        }
    }
}