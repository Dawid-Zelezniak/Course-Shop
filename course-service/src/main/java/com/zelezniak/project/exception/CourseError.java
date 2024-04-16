package com.zelezniak.project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CourseError {

    COURSE_NOT_FOUND("Course not found!"),
    COURSE_ALREADY_EXISTS("Such course already exists!"),
    COURSE_PRICE_EXCEPTION("Course price can not be less than 0");
    private final String message;
}
    