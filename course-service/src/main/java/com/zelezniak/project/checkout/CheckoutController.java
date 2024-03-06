package com.zelezniak.project.checkout;

import com.zelezniak.project.course.CourseService;
import com.zelezniak.project.dto.PaymentInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

import static com.zelezniak.project.controller.AttributesAndTemplatesNames.*;


@Controller
@Slf4j
@RequiredArgsConstructor
public class CheckoutController {

    private final CourseService courseService;

    @Value("${stripe.api.publicKey}")
    private String publicKey;

    @PostMapping({"/checkout"})
    public String showCard(@ModelAttribute PaymentInfo paymentInfo, Model model) {
        model.addAttribute(PUBLIC_KEY_ATTRIBUTE, publicKey);
        model.addAttribute(AMOUNT_ATTRIBUTE, paymentInfo.getAmount());
        model.addAttribute(EMAIL_ATTRIBUTE, paymentInfo.getEmail());
        model.addAttribute(PRODUCT_NAME_ATTRIBUTE, paymentInfo.getProductName());
        return CHECKOUT_VIEW;
    }

    @GetMapping("/success/payment")
    public String handleSuccessPayment(Principal principal, @RequestParam String productName) {
        String email = principal.getName();
        courseService.addBoughtCourseAndOrderForUser(email, productName);
        return PAYMENT_SUCCESS_VIEW;
    }
}
