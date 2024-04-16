package com.zelezniak.project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserException extends RuntimeException{

    private UserError userError;
}
