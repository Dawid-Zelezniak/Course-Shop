package com.zelezniak.emailservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<ErrorInfo> courseExceptionHandler(EmailException exception) {

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        switch (exception.getCourseError()) {
            case EMAIL_INFO_NOT_FOUND-> httpStatus = HttpStatus.NOT_FOUND;

        }
        return ResponseEntity.status(httpStatus).body(new ErrorInfo(exception.getCourseError().getMessage()));
    }
}