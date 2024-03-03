package com.zelezniak.project.controller;

import com.zelezniak.project.dto.PaymentInfo;
import com.zelezniak.project.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
@Slf4j
@RequiredArgsConstructor
public class CheckoutController {


    private final CourseService courseService;

    @Value("${stripe.api.publicKey}")
    private String publicKey;

    @PostMapping({"/checkout"})
    public String showCard(@ModelAttribute PaymentInfo paymentInfo, Model model) {
        model.addAttribute("publicKey", publicKey);
        model.addAttribute("amount", paymentInfo.getAmount());
        model.addAttribute("email", paymentInfo.getEmail());
        model.addAttribute("productName", paymentInfo.getProductName());
        return "checkout-form";
    }

    @GetMapping("/success/payment")
    public String handleSuccessPayment(Principal principal, @RequestParam String productName) {
        String email = principal.getName();
        courseService.addBoughtCourseAndOrderForUser(email, productName);
        return "courses";
    }


}
