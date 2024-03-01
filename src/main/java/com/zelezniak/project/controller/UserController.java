package com.zelezniak.project.controller;


import java.util.List;

import com.zelezniak.project.entity.CourseAuthor;
import com.zelezniak.project.entity.Student;
import com.zelezniak.project.service.AuthorService;
import com.zelezniak.project.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final StudentService studentService;
    private final AuthorService authorService;

    @GetMapping("/all/students")
    public ModelAndView allStudents() {
        ModelAndView modelAndView = new ModelAndView("students");
        List<Student> allStudents = this.studentService.getAllStudents();
        modelAndView.addObject("students", allStudents);
        return modelAndView;
    }

    @GetMapping("/all/authors")
    public ModelAndView allAuthors() {
        ModelAndView modelAndView = new ModelAndView("authors");
        List<CourseAuthor> allAuthors = authorService.getAllAuthors();
        modelAndView.addObject("authors", allAuthors);
        return modelAndView;
    }

}