package com.zelezniak.project.controller;

import com.zelezniak.project.dto.PaymentInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@Slf4j
public class CheckoutController {
    @Value("${stripe.api.publicKey}")
    private String publicKey;

    @PostMapping({"/checkout"})
    public String showCard(@ModelAttribute PaymentInfo paymentInfo, Model model) {
        model.addAttribute("publicKey", this.publicKey);
        model.addAttribute("amount", paymentInfo.getAmount());
        model.addAttribute("email", paymentInfo.getEmail());
        model.addAttribute("productName", paymentInfo.getProductName());
        log.info(paymentInfo.getEmail());
        log.info(paymentInfo.getProductName());
        log.info("" + paymentInfo.getAmount());
        //dane są poprawnie przekazywane do formularza a następnie do pliku checkout.js
        return "checkout-form";
    }
}