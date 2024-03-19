package com.zelezniak.project.order;

import com.zelezniak.project.author.AuthorService;
import com.zelezniak.project.author.CourseAuthor;
import com.zelezniak.project.student.Student;
import com.zelezniak.project.student.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Set;

import static com.zelezniak.project.common.AttributesAndTemplatesNames.*;

@RequiredArgsConstructor
@Controller
public class OrderController {

    private final StudentService studentService;
    private final AuthorService authorService;

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
