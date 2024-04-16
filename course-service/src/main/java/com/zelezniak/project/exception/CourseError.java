package com.zelezniak.project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomErrors {

    COURSE_NOT_FOUND("Course not found!"),
    COURSE_ALREADY_EXISTS("Such course already exists!");
    private final String message;
}
    