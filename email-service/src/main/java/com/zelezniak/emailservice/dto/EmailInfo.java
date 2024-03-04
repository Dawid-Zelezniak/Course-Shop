package com.zelezniak.emailservice.dto;

import lombok.Data;

@Data
public class EmailInfo {

    private String email;
    private String firstName;
    private String lastName;
    private String title;
    private Double price;
    private String orderId;
}
