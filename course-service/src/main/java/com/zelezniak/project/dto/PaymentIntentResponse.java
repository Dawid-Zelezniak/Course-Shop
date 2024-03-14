package com.zelezniak.project.dto;

import lombok.Data;

@Data
public final class PaymentIntentResponse {

    private final String intentID;
    private final String clientSecret;
}
