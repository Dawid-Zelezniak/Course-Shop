package com.zelezniak.project.valueobjects;

import com.zelezniak.project.exception.CourseError;
import com.zelezniak.project.exception.CourseException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Embeddable
@Getter
@Slf4j
public final class Money {

    @Column(nullable = false)
    private BigDecimal money;

    public Money(String money) {
        BigDecimal value = BigDecimal.valueOf(Double.parseDouble(money));
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new CourseException(CourseError.COURSE_PRICE_EXCEPTION);
        }
        this.money = value.setScale(2, RoundingMode.HALF_UP);
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
