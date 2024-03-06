package com.zelezniak.project.checkout;

import com.zelezniak.project.course.Course;
import com.zelezniak.project.dto.PaymentInfo;
import org.springframework.stereotype.Service;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    @Override
    public PaymentInfo createPaymentInfo(Course courseToBuy, String email) {
        return PaymentInfo.builder()
                .amount(courseToBuy.getPrice())
                .email(email)
                .productName(courseToBuy.getTitle())
                .build();
    }
}