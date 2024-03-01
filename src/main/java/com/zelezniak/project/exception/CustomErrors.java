package com.zelezniak.project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomErrors {
    COURSE_NOT_FOUND("Course not found!"),
    COURSE_ALREADY_EXISTS("Such course already exists!"),
    USER_ALREADY_EXISTS("User with such email already exists!"),
    EMAIL_IN_WRONG_FORMAT("Your email is in wrong format!"),
    USER_NOT_FOUND("Such user does not exists!");

    private final String message;

}
    