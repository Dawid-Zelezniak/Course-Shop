package com.zelezniak.project.dto;

import com.zelezniak.project.course.Course;
import com.zelezniak.project.valueobjects.Money;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class PaymentInfo {

    private Money amount;
    private String email;
    private String productName;

    public static final class PaymentInfoBuilder {

        public static PaymentInfo buildPaymentInfo(Course courseToBuy, String email) {
            return PaymentInfo.builder()
                    .amount(courseToBuy.getPrice())
                    .email(email)
                    .productName(courseToBuy.getTitle())
                    .build();
        }
    }
}