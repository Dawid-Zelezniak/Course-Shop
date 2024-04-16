package com.zelezniak.project.valueobjects;

import com.zelezniak.project.exception.UserError;
import com.zelezniak.project.exception.UserException;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Objects;

@Embeddable
@Getter
public class UserName {

    private final String firstName;
    private final String lastName;

    public UserName(String firstName, String lastName) {
        if (firstName == null || firstName.length() < 2) {
            throw new UserException(UserError.FIRST_NAME_TOO_SHORT);
        }
        if (lastName == null || lastName.length() < 2) {
            throw new UserException(UserError.LAST_NAME_TOO_SHORT);
        }
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserName() {
        this.firstName = null;
        this.lastName = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserName userName = (UserName) o;
        return Objects.equals(firstName, userName.firstName) && Objects.equals(lastName, userName.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
