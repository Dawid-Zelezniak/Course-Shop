package com.zelezniak.project.checkout;

import com.zelezniak.project.dto.PaymentInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

import static com.zelezniak.project.common.AttributesAndTemplatesNames.*;


@Controller
@RequiredArgsConstructor
public final class CheckoutController {

    private final Checkout checkout;

    @PostMapping("/checkout")
    public String showCard(@ModelAttribute PaymentInfo paymentInfo, Model model) {
        checkout.addPaymentAttributesToModel(model, paymentInfo);
        return CHECKOUT_VIEW;
    }

    @GetMapping("/success/payment")
    public String handleSuccessPayment(Principal principal, @RequestParam String courseName) {
        checkout.addBoughtCourseAndOrderForUser(principal.getName(), courseName);
        return PAYMENT_SUCCESS_VIEW;
    }
}
