package com.zelezniak.project.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentInfo {
    private final Double amount;
    private final String email;
    private final String productName;

}