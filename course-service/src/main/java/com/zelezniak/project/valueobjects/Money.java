package com.zelezniak.project.valueobjects;

import com.zelezniak.project.exception.CourseError;
import com.zelezniak.project.exception.CourseException;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
@Getter
public class Money {
    private final BigDecimal money;

    public Money(String money) {
        double value = Double.parseDouble(money);
        if (value < 0) {
            throw new CourseException(CourseError.COURSE_PRICE_EXCEPTION);
        }
        this.money = format(value);
    }

    private BigDecimal format(double money) {
        String formatted = String.format("%.2f",money);
        return new BigDecimal(formatted);
    }

    public Money() {
        this.money = null;
    }

    public long convertToCents() {
        return Math.round(money.doubleValue() * 100);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money1 = (Money) o;
        return Objects.equals(money, money1.money);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(money);
    }

    @Override
    public String toString() {
        return money.toString();
    }
}
