package com.zelezniak.project.course;

import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

public interface CourseView {

    ModelAndView availableCoursesView(Principal principal);

    ModelAndView prepareOrderInfo(Long courseId, Principal principal);

    ModelAndView addCourseView(Principal principal);

    ModelAndView handleCourseSaving(BindingResult bindingResult, Course course);

    ModelAndView getCourseDetailsView(Long courseId);

    ModelAndView courseUpdateView(Long courseId);

    ModelAndView handleCourseUpdate(Long courseId, BindingResult bindingResult, Course course);

    ModelAndView coursesBoughtByUserView(Principal principal);

    ModelAndView coursesCreatedByAuthorView(Principal principal);
}
