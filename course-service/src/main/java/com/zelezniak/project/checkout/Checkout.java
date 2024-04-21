package com.zelezniak.project.checkout;

import com.zelezniak.project.dto.PaymentInfo;
import org.springframework.ui.Model;

public interface Checkout {
    void addPaymentAttributesToModel(Model model, PaymentInfo paymentInfo);

    void addBoughtCourseAndOrderForUser(String name, String courseName);
}
