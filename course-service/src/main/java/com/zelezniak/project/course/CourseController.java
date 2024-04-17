package com.zelezniak.project.course;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

import static com.zelezniak.project.common.AttributesAndTemplatesNames.*;

@Controller
@RequiredArgsConstructor
public final class CourseController {

    private final CourseViewService courseViewService;

    @GetMapping("/courses")
    public ModelAndView availableCourses(Principal principal) {
        return courseViewService.availableCoursesView(principal);
    }

    @GetMapping("/courses/payment/info")
    public ModelAndView prepareOrder(@RequestParam Long courseId, Principal principal) {
        return courseViewService.prepareOrderInfo(courseId, principal);
    }

    @GetMapping("/add/courses/form")
    public ModelAndView addCourseForm(Principal principal) {
        return courseViewService.addCourseView(principal);
    }

    @PostMapping("/save/courses")
    public ModelAndView saveCourse(@ModelAttribute(COURSE_ATTRIBUTE) @Valid Course course,
                                   BindingResult bindingResult) {
        return courseViewService.handleCourseSaving(bindingResult, course);
    }

    @GetMapping("/courses/details")
    public ModelAndView courseDetails(@RequestParam Long courseId) {
        return courseViewService.getCourseDetailsView(courseId);
    }

    @GetMapping("/update/courses")
    public ModelAndView courseUpdateForm(@RequestParam Long courseId) {
        return courseViewService.courseUpdateView(courseId);
    }

    @PostMapping("/update/courses/")
    public ModelAndView updateCourse(@RequestParam Long courseId,
                                     @ModelAttribute(COURSE_ATTRIBUTE) @Valid Course course,
                                     BindingResult bindingResult) {
        return courseViewService.handleCourseUpdate(courseId, bindingResult, course);
    }

    @GetMapping("/purchased/courses")
    public ModelAndView coursesBoughtByUser(Principal principal) {
        return courseViewService.coursesBoughtByUserView(principal);
    }

    @GetMapping("/author/courses")
    public ModelAndView coursesCreatedByAuthor(Principal principal) {
        return courseViewService.coursesCreatedByAuthorView(principal);
    }
}