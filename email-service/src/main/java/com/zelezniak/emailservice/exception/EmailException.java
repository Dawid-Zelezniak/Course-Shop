package com.zelezniak.emailservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailException extends RuntimeException {

    private CustomErrors courseError;
}