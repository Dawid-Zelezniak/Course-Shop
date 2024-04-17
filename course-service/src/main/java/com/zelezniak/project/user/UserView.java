package com.zelezniak.project.user;

import org.springframework.web.servlet.ModelAndView;

public interface UserView {
    ModelAndView allStudentsView();

    ModelAndView allAuthorsView();

    ModelAndView authorDetailsView(Long authorId);

    ModelAndView studentDetailsView(Long studentId);
}
