package com.zelezniak.project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class ErrorInfo {

    private String errorMessage;

    @Override
    public String toString() {
        return "ErrorInfo{" +
                "errorMessage='" + errorMessage + '\'' +
                '}';
    }
}