package com.zelezniak.project.checkout;

import com.zelezniak.project.course.CourseService;
import com.zelezniak.project.dto.PaymentInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import static com.zelezniak.project.common.AttributesAndTemplatesNames.*;
import static com.zelezniak.project.common.AttributesAndTemplatesNames.PRODUCT_NAME_ATTRIBUTE;

@Service
@RequiredArgsConstructor
public class CheckoutService implements Checkout {

    @Value("${stripe.api.publicKey}")
    private String publicKey;

    private final CourseService courseService;

    public void addPaymentAttributesToModel(Model model, PaymentInfo paymentInfo) {
        model.addAttribute(PUBLIC_KEY_ATTRIBUTE, publicKey);
        model.addAttribute(AMOUNT_ATTRIBUTE, paymentInfo.getAmount());
        model.addAttribute(EMAIL_ATTRIBUTE, paymentInfo.getEmail());
        model.addAttribute(PRODUCT_NAME_ATTRIBUTE, paymentInfo.getProductName());
    }

    public void addBoughtCourseAndOrderForUser(String email, String courseName) {
        courseService.addBoughtCourseAndOrderForUser(email, courseName);
    }
}
