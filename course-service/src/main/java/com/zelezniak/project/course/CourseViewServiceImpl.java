package com.zelezniak.project.course;

import com.zelezniak.project.author.AuthorService;
import com.zelezniak.project.author.CourseAuthor;
import com.zelezniak.project.common.FormErrorHandler;
import com.zelezniak.project.dto.PaymentInfo;
import com.zelezniak.project.exception.CourseException;
import com.zelezniak.project.exception.ErrorInfo;
import com.zelezniak.project.student.Student;
import com.zelezniak.project.student.StudentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import static com.zelezniak.project.common.AttributesAndTemplatesNames.*;
import static com.zelezniak.project.common.AttributesAndTemplatesNames.PAYMENT_INFO_ATTRIBUTE;

@Service
@RequiredArgsConstructor
public class CourseViewServiceImpl implements CourseViewService {

    private final CourseService courseService;
    private final AuthorService authorService;
    private final StudentService studentService;
    private final HttpSession httpSession;

    @Value("${categories}")
    private List<String>categories;

    public ModelAndView availableCoursesView(Principal principal) {
        ModelAndView modelAndView = new ModelAndView(COURSES_LIST_VIEW);
        List<Course> courses = courseService.getAllAvailableCourses(principal.getName());
        modelAndView.addObject(COURSES_ATTRIBUTE, courses);
        return modelAndView;
    }

    public ModelAndView prepareOrderInfo(Long courseId, Principal principal) {
        ModelAndView modelAndView = new ModelAndView(PAYMENT_INFORMATION_VIEW);
        PaymentInfo paymentInfo = courseService.prepareOrderInfo(courseId, principal);
        modelAndView.addObject(PAYMENT_INFO_ATTRIBUTE, paymentInfo);
        return modelAndView;
    }

    public ModelAndView addCourseView(Principal principal) {
        ModelAndView modelAndView = new ModelAndView(ADD_COURSES_VIEW);
        CourseAuthor author = authorService.findByEmail(principal.getName());
        httpSession.setAttribute(AUTHOR_ID_ATTRIBUTE, author.getAuthorId());
        modelAndView.addObject(COURSE_ATTRIBUTE, new Course());
        modelAndView.addObject(CATEGORIES_ATTRIBUTE,categories);
        return modelAndView;
    }

    public ModelAndView handleCourseSaving(BindingResult bindingResult, Course course) {
        ModelAndView modelAndView = new ModelAndView(ADD_COURSES_VIEW);
        Long authorId = (Long)httpSession.getAttribute(AUTHOR_ID_ATTRIBUTE);
        ModelAndView errors = FormErrorHandler.getErrors(bindingResult, modelAndView);
        if (errors != null) {return errors;}
        else {
            try {
                courseService.addCourse(course, authorId);
                modelAndView.addObject(INFO_ATTRIBUTE, "New course has been created!");
                return modelAndView;
            } catch (CourseException ex) {
                return modelAndView.addObject(
                        ERROR_INFO_ATTRIBUTE, new ErrorInfo(ex.getCourseError().getMessage()));
            }
        }
    }

    public ModelAndView getCourseDetailsView(Long courseId) {
        ModelAndView modelAndView = new ModelAndView(COURSE_DETAILS_VIEW);
        Course course = courseService.findById(courseId);
        modelAndView.addObject(COURSE_ATTRIBUTE, course);
        modelAndView.addObject(PARTICIPANTS_ATTRIBUTE, course.countCourseParticipants());
        return modelAndView;
    }

    public ModelAndView courseUpdateView(Long courseId) {
        ModelAndView modelAndView = new ModelAndView(UPDATE_COURSE_VIEW);
        Course course = courseService.findById(courseId);
        modelAndView.addObject(COURSE_ATTRIBUTE, course);
        return modelAndView;
    }

    public ModelAndView handleCourseUpdate(Long courseId, BindingResult bindingResult, Course course) {
        ModelAndView modelAndView = new ModelAndView(UPDATE_COURSE_VIEW);
        ModelAndView errors = FormErrorHandler.getErrors(bindingResult, modelAndView);
        if (errors != null) {return errors;}
        else {
            try {
                courseService.updateCourse(courseId, course);
                modelAndView.addObject(INFO_ATTRIBUTE, "course updated successfully");
            } catch (CourseException ex) {
                modelAndView.addObject(ERROR_INFO_ATTRIBUTE, new ErrorInfo(ex.getCourseError().getMessage()));
            }return modelAndView;
        }
    }

    public ModelAndView coursesBoughtByUserView(Principal principal) {
        ModelAndView modelAndView = new ModelAndView(USER_COURSES_VIEW);
        String email = principal.getName();
        CourseAuthor author = authorService.findByEmail(email);
        Student student = studentService.findByEmail(email);
        Set<Course> userCourses = author != null ? author.getBoughtCourses() : student.getBoughtCourses();
        modelAndView.addObject(COURSES_ATTRIBUTE, userCourses);
        return modelAndView;
    }

    public ModelAndView coursesCreatedByAuthorView(Principal principal) {
        ModelAndView modelAndView = new ModelAndView(CREATED_BY_AUTHOR_VIEW);
        String authorEmail = principal.getName();
        Set<Course> createdByAuthor = authorService.findByEmail(authorEmail).getCreatedByAuthor();
        modelAndView.addObject(COURSES_ATTRIBUTE, createdByAuthor);
        return modelAndView;
    }
}
