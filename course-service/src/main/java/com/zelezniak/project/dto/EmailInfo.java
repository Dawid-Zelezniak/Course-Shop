package com.zelezniak.project.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class EmailInfo implements Serializable {

    private String email;
    private String firstName;
    private String lastName;
    private String title;
    private Double price;
    private String orderId;
}
