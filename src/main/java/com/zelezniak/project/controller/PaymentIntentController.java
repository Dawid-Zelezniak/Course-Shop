package com.zelezniak.project.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentIntentCreateParams.AutomaticPaymentMethods;
import com.zelezniak.project.dto.PaymentInfo;
import com.zelezniak.project.dto.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PaymentIntentController {

    @PostMapping({"/payment/intent"})
    public Response createPaymentIntent(@RequestBody PaymentInfo paymentInfo) throws StripeException {
        //to sie nie wykonuje
        log.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount((long) (paymentInfo.getAmount() * 100))
                .putMetadata("productName", paymentInfo.getProductName())
                .setCurrency("usd")
                .setAutomaticPaymentMethods(AutomaticPaymentMethods.builder()
                        .setEnabled(true).build()).build();

        PaymentIntent intent = PaymentIntent.create(params);
        return new Response(intent.getId(),
                intent.getClientSecret());
    }
}