package com.zelezniak.emailservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomErrors {

    EMAIL_INFO_NOT_FOUND("Email info not found!");

    private final String message;
}
    