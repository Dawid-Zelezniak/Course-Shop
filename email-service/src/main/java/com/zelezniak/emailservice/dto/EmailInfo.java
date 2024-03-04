package com.zelezniak.emailservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmailInfo implements Serializable {

    private String email;
    private String firstName;
    private String lastName;
    private String title;
    private Double price;
    private String orderId;
}
