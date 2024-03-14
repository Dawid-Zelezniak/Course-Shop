package com.zelezniak.project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class CourseException extends RuntimeException {

    private CustomErrors courseError;
}