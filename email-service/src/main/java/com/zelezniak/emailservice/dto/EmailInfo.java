package com.zelezniak.emailservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmailInfo implements Serializable {

    private String email;
    private UserName userName;
    private String title;
    private Money price;
    private String orderId;
}
