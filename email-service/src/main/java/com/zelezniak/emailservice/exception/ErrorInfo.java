package com.zelezniak.emailservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
final class ErrorInfo {

    private String errorMessage;
}