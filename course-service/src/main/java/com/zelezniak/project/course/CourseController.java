package com.zelezniak.project.course;


import com.zelezniak.project.author.AuthorService;
import com.zelezniak.project.author.CourseAuthor;
import com.zelezniak.project.checkout.CheckoutService;
import com.zelezniak.project.controller.FormValidationManager;
import com.zelezniak.project.dto.PaymentInfo;
import com.zelezniak.project.exception.CourseException;
import com.zelezniak.project.exception.ErrorInfo;
import com.zelezniak.project.order.Order;
import com.zelezniak.project.order.OrderService;
import com.zelezniak.project.student.Student;
import com.zelezniak.project.student.StudentService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import static com.zelezniak.project.controller.AttributesAndTemplatesNames.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CourseController {

    private final HttpSession httpSession;
    private final CourseService courseService;
    private final AuthorService authorService;
    private final CheckoutService checkoutService;
    private final StudentService studentService;

    @GetMapping("/courses")
    public ModelAndView availableCourses(Principal principal) {
        ModelAndView modelAndView = new ModelAndView(COURSES_LIST_VIEW);
        List<Course> courses = courseService.getAllAvailableCourses(principal.getName());
        modelAndView.addObject(COURSES_ATTRIBUTE, courses);
        return modelAndView;
    }

    @GetMapping("/courses/payment/info")
    public ModelAndView prepareOrder(@RequestParam Long courseId, Principal principal) {
        ModelAndView modelAndView = new ModelAndView(PAYMENT_INFORMATION_VIEW);
        Course courseToBuy = courseService.findById(courseId);
        PaymentInfo paymentInfo = checkoutService.createPaymentInfo(courseToBuy, principal.getName());
        modelAndView.addObject(PAYMENT_INFO_ATTRIBUTE, paymentInfo);
        return modelAndView;
    }

    @GetMapping("/add/courses/form")
    public ModelAndView addCourseForm(Principal principal) {
        ModelAndView modelAndView = new ModelAndView(ADD_COURSES_VIEW);
        String email = principal.getName();
        CourseAuthor authorFromDb = authorService.findByEmail(email);
        httpSession.setAttribute(AUTHOR_ID_ATTRIBUTE,authorFromDb.getAuthorId());
        modelAndView.addObject(COURSE_ATTRIBUTE, new Course());
        return modelAndView;
    }

    @PostMapping("/save/courses")
    public ModelAndView saveCourse(@ModelAttribute(COURSE_ATTRIBUTE) @Valid Course course,
                                   BindingResult bindingResult) {
        Long authorId = (Long)httpSession.getAttribute(AUTHOR_ID_ATTRIBUTE);
        ModelAndView modelAndView = new ModelAndView(ADD_COURSES_VIEW);
        ModelAndView errors = FormValidationManager.getErrors(bindingResult, modelAndView);
        if (errors != null) {return errors;}
        else {
            try {
                courseService.addCourse(course, authorId);
                modelAndView.addObject(INFO_ATTRIBUTE, "New course has been created!");
                return modelAndView;
            } catch (CourseException ex) {
                return modelAndView.addObject(
                        ERROR_INFO_ATTRIBUTE, new ErrorInfo(ex.getCourseError().getMessage()));}
        }
    }

    @GetMapping("/courses/details")
    public ModelAndView courseDetails(@RequestParam Long courseId) {
        ModelAndView modelAndView = new ModelAndView(COURSE_DETAILS_VIEW);
        Course course = courseService.findById(courseId);
        int participantsNumber = course.countTotalParticipants();
        modelAndView.addObject(COURSE_ATTRIBUTE, course);
        modelAndView.addObject(PARTICIPANTS_ATTRIBUTE, participantsNumber);
        return modelAndView;
    }

    @GetMapping("/author/courses")
    public ModelAndView coursesCreatedByAuthor(Principal principal) {
        ModelAndView modelAndView = new ModelAndView(CREATED_BY_AUTHOR_VIEW);
        String authorEmail = principal.getName();
        Set<Course> createdByAuthor = authorService.findByEmail(authorEmail).getCreatedByAuthor();
        modelAndView.addObject(COURSES_ATTRIBUTE, createdByAuthor);
        return modelAndView;
    }

    @GetMapping("/update/courses")
    public ModelAndView courseUpdateForm(@RequestParam Long courseId) {
        ModelAndView modelAndView = new ModelAndView(UPDATE_COURSE_VIEW);
        Course course = courseService.findById(courseId);
        modelAndView.addObject(COURSE_ATTRIBUTE, course);
        return modelAndView;
    }

    @PostMapping("/update/courses/")
    public ModelAndView updateCourse(@RequestParam Long courseId, @ModelAttribute(COURSE_ATTRIBUTE) @Valid Course course, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView(UPDATE_COURSE_VIEW);
        ModelAndView errors = FormValidationManager.getErrors(bindingResult, modelAndView);
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

    @GetMapping("/purchased/courses")
    public ModelAndView coursesBoughtByUser(Principal principal) {
        ModelAndView modelAndView = new ModelAndView(USER_COURSES_VIEW);
        String email = principal.getName();
        CourseAuthor author = authorService.findByEmail(email);
        Student student = studentService.findByEmail(email);
        Set<Course> userCourses = author != null ? author.getBoughtCourses() : student.getBoughtCourses();
        modelAndView.addObject(COURSES_ATTRIBUTE, userCourses);
        return modelAndView;
    }

    @GetMapping({"/user/orders"})
    public ModelAndView userOrders(Principal principal) {
        ModelAndView modelAndView = new ModelAndView(USER_ORDERS_VIEW);
        String email = principal.getName();
        CourseAuthor author = authorService.findByEmail(email);
        Student student = studentService.findByEmail(email);
        Set<Order> orders = author != null ? author.getAuthorOrders() : student.getStudentOrders();
        double totalPrice = OrderService.totalOrdersPrice(orders);
        modelAndView.addObject(TOTAL_PRICE_ATTRIBUTE, totalPrice);
        modelAndView.addObject(ORDERS_ATTRIBUTE, orders);
        return modelAndView;
    }
}