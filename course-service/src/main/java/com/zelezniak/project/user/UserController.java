package com.zelezniak.project.user;


import com.zelezniak.project.author.AuthorService;
import com.zelezniak.project.author.CourseAuthor;
import com.zelezniak.project.course.Course;
import com.zelezniak.project.order.Order;
import com.zelezniak.project.student.Student;
import com.zelezniak.project.student.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Set;

import static com.zelezniak.project.common.AttributesAndTemplatesNames.*;

@Controller
@RequiredArgsConstructor
public final class UserController {

    private final StudentService studentService;
    private final AuthorService authorService;

    @GetMapping("/all/students")
    public ModelAndView allStudents() {
        ModelAndView modelAndView = new ModelAndView(STUDENTS_LIST_VIEW);
        List<Student> allStudents = studentService.getAllStudents();
        modelAndView.addObject(STUDENTS_ATTRIBUTE, allStudents);
        return modelAndView;
    }

    @GetMapping("/all/authors")
    public ModelAndView allAuthors() {
        ModelAndView modelAndView = new ModelAndView(AUTHORS_LIST_VIEW);
        List<CourseAuthor> allAuthors = authorService.getAllAuthors();
        modelAndView.addObject(AUTHORS_ATTRIBUTE, allAuthors);
        return modelAndView;
    }

    @GetMapping("/author/details")
    public ModelAndView authorDetails(@RequestParam Long authorId) {
        ModelAndView modelAndView = new ModelAndView(AUTHOR_DETAILS_VIEW);
        CourseAuthor author = authorService.getById(authorId);
        Set<Course> createdByAuthor = author.getCreatedByAuthor();
        Set<Course> boughtCourses = author.getBoughtCourses();
        Set<Order> authorOrders = author.getAuthorOrders();
        modelAndView.addObject(AUTHOR_ATTRIBUTE, author);
        modelAndView.addObject(COURSES_ATTRIBUTE,createdByAuthor);
        modelAndView.addObject(BOUGHT_COURSES_ATTRIBUTE,boughtCourses);
        modelAndView.addObject(ORDERS_ATTRIBUTE,authorOrders);
        return modelAndView;
    }

    @GetMapping("/student/details")
    public ModelAndView studentDetails(@RequestParam Long studentId) {
        ModelAndView modelAndView = new ModelAndView(STUDENT_DETAILS_VIEW);
        Student student = studentService.findById(studentId);
        Set<Course> boughtCourses = student.getBoughtCourses();
        Set<Order> studentOrders = student.getStudentOrders();
        modelAndView.addObject(BOUGHT_COURSES_ATTRIBUTE,boughtCourses);
        modelAndView.addObject(ORDERS_ATTRIBUTE,studentOrders);
        modelAndView.addObject(STUDENT_ATTRIBUTE,student);
        return modelAndView;
    }
}