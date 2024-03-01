package com.zelezniak.project.service;

import com.zelezniak.project.dto.PaymentInfo;
import com.zelezniak.project.entity.Course;


public interface CheckoutService {
    PaymentInfo createPaymentInfo(Course courseToBuy, String email);


}
    