package com.zelezniak.project.user;

import com.zelezniak.project.author.AuthorService;
import com.zelezniak.project.author.CourseAuthor;
import com.zelezniak.project.student.Student;
import com.zelezniak.project.student.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import static com.zelezniak.project.common.AttributesAndTemplatesNames.*;

@Service
@RequiredArgsConstructor
public class UserViewService implements UserView {

    private final StudentService studentService;
    private final AuthorService authorService;

    public ModelAndView allStudentsView() {
        ModelAndView modelAndView = new ModelAndView(STUDENTS_LIST_VIEW);
        modelAndView.addObject(STUDENTS_ATTRIBUTE, studentService.getAllStudents());
        return modelAndView;
    }

    public ModelAndView allAuthorsView() {
        ModelAndView modelAndView = new ModelAndView(AUTHORS_LIST_VIEW);
        modelAndView.addObject(AUTHORS_ATTRIBUTE, authorService.getAllAuthors());
        return modelAndView;
    }

    public ModelAndView authorDetailsView(Long authorId) {
        ModelAndView modelAndView = new ModelAndView(AUTHOR_DETAILS_VIEW);
        CourseAuthor author = authorService.findById(authorId);
        modelAndView.addObject(AUTHOR_ATTRIBUTE, author);
        modelAndView.addObject(COURSES_ATTRIBUTE,author.getCreatedByAuthor());
        modelAndView.addObject(BOUGHT_COURSES_ATTRIBUTE,author.getBoughtCourses());
        modelAndView.addObject(ORDERS_ATTRIBUTE,author.getAuthorOrders());
        return modelAndView;
    }

    public ModelAndView studentDetailsView(Long studentId) {
        ModelAndView modelAndView = new ModelAndView(STUDENT_DETAILS_VIEW);
        Student student = studentService.findById(studentId);
        modelAndView.addObject(BOUGHT_COURSES_ATTRIBUTE,student.getBoughtCourses());
        modelAndView.addObject(ORDERS_ATTRIBUTE,student.getStudentOrders());
        modelAndView.addObject(STUDENT_ATTRIBUTE,student);
        return modelAndView;
    }
}
