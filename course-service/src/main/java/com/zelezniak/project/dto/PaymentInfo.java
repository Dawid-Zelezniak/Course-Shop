package com.zelezniak.project.dto;

import com.zelezniak.project.course.Course;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class PaymentInfo {

    private final Double amount;
    private final String email;
    private final String productName;

    public static final class PaymentInfoBuilder{

        public static PaymentInfo buildPaymentInfo(Course courseToBuy, String email) {
            return PaymentInfo.builder()
                    .amount(courseToBuy.getPrice())
                    .email(email)
                    .productName(courseToBuy.getTitle())
                    .build();
        }
    }
}