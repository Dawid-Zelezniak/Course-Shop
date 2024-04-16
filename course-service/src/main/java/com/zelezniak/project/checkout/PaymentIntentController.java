package com.zelezniak.project.checkout;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.zelezniak.project.dto.PaymentInfo;
import com.zelezniak.project.dto.PaymentIntentResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
final class PaymentIntentController {

    @PostMapping("/payment/intent")
    @ResponseBody
    public PaymentIntentResponse createPaymentIntent(@RequestBody PaymentInfo paymentInfo)
            throws StripeException {
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(paymentInfo.getAmount().convertToCents())
                        .putMetadata("productName",
                                paymentInfo.getProductName())
                        .setCurrency("usd")
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams
                                        .AutomaticPaymentMethods
                                        .builder()
                                        .setEnabled(true)
                                        .build()
                        ).build();

        PaymentIntent intent = PaymentIntent.create(params);
        return new PaymentIntentResponse(intent.getId(), intent.getClientSecret());
    }
}