package com.zelezniak.project.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserData {

    @NotBlank(message = "Password can not be blank")
    @Size(min = 5, message = "Password must contains at least 5 characters")
    private String password;

    @NotBlank(message = "First Name can not be blank")
    @Size(min = 2, message = "First Name must contains at least 2 characters")
    private String firstName;

    @NotBlank(message = "Last Name can not be blank")
    @Size(min = 2, message = "Last Name must contains at least 2 characters")
    private String lastName;

    @NotBlank(message = "Email can not be blank.")
    private String email;

    private String role;
}