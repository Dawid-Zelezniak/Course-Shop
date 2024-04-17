package com.zelezniak.project.user;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public final class UserController {

    private final UserView userView;

    @GetMapping("/all/students")
    public ModelAndView showAllStudents() {
      return userView.allStudentsView();
    }

    @GetMapping("/all/authors")
    public ModelAndView showAllAuthors() {
        return userView.allAuthorsView();
    }

    @GetMapping("/author/details")
    public ModelAndView showAuthorDetails(@RequestParam Long authorId) {
    return userView.authorDetailsView(authorId);
    }

    @GetMapping("/student/details")
    public ModelAndView showStudentDetails(@RequestParam Long studentId) {
    return userView.studentDetailsView(studentId);
    }
}