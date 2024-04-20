package com.zelezniak.project.valueobjects;

import com.zelezniak.project.exception.UserError;
import com.zelezniak.project.exception.UserException;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Objects;

@Embeddable
@Getter
public final class UserCredentials {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private final String email;
    private final String password;

    public UserCredentials(String email, String password) {
        if (!email.matches(EMAIL_PATTERN)) {
            throw new UserException(UserError.EMAIL_IN_WRONG_FORMAT);
        }
        if (password == null || password.length() <5) {
            throw new UserException(UserError.PASSWORD_TOO_SHORT);
        }
        this.email = email;
        this.password = password;
    }

    public UserCredentials() {
        this.email = null;
        this.password = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCredentials that = (UserCredentials) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }
}
