package com.zelezniak.project.checkout;

import com.zelezniak.project.course.Course;
import com.zelezniak.project.dto.PaymentInfo;


public interface CheckoutService {

    PaymentInfo createPaymentInfo(Course courseToBuy, String email);
}
    