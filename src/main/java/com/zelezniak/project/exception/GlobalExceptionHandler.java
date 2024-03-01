package com.zelezniak.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CourseException.class)
    public ResponseEntity<ErrorInfo> courseExceptionHandler(CourseException exception) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        switch (exception.getCourseError()) {
            case COURSE_NOT_FOUND,
                    USER_NOT_FOUND -> httpStatus = HttpStatus.NOT_FOUND;

            case EMAIL_IN_WRONG_FORMAT,
                    COURSE_ALREADY_EXISTS,
                    USER_ALREADY_EXISTS -> httpStatus = HttpStatus.CONFLICT;
        }
        return ResponseEntity.status(httpStatus).body(new ErrorInfo(exception.getCourseError().getMessage()));
    }
}