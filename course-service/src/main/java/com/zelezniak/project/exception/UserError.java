package com.zelezniak.project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserError {

    USER_NOT_FOUND("Such user does not exists!"),
    USER_ALREADY_EXISTS("User with such email already exists!"),
    EMAIL_IN_WRONG_FORMAT("Your email is in wrong format!"),
    FIRST_NAME_TOO_SHORT("First name is too short!"),
    LAST_NAME_TOO_SHORT("Last name is too short!"),
    PASSWORD_TOO_SHORT("Password is too short!");

    private final String message;
}
