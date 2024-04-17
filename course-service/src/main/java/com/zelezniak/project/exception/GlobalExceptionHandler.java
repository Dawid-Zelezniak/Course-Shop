package com.zelezniak.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public final class GlobalExceptionHandler {

    private static final HttpStatus DEFAULT_ERROR_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    @ExceptionHandler(CourseException.class)
    public ResponseEntity<ErrorInfo> handleException(CourseException exception) {

        HttpStatus httpStatus = DEFAULT_ERROR_STATUS;
        switch (exception.getCourseError()) {
            case COURSE_NOT_FOUND -> httpStatus = HttpStatus.NOT_FOUND;
            case COURSE_ALREADY_EXISTS -> httpStatus = HttpStatus.CONFLICT;
        }
        return ResponseEntity.status(httpStatus).body(new ErrorInfo(exception.getCourseError().getMessage()));
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorInfo> handleException(UserException exception) {
        HttpStatus httpStatus = DEFAULT_ERROR_STATUS;
        switch (exception.getUserError()) {
            case USER_NOT_FOUND -> httpStatus = HttpStatus.NOT_FOUND;
            case EMAIL_IN_WRONG_FORMAT,
                 USER_ALREADY_EXISTS -> httpStatus = HttpStatus.CONFLICT;
        }
        return ResponseEntity.status(httpStatus).body(new ErrorInfo(exception.getUserError().getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> handleException(Exception exception) {
        return ResponseEntity.status(DEFAULT_ERROR_STATUS).body(new ErrorInfo(exception.getMessage()));
    }
}