package com.daretodo.keyboardnotifier.product.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Period {
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public static Period of(LocalDateTime startDate, LocalDateTime endDate) {
        return new Period(startDate, endDate);
    }

    public static Period from(LocalDateTime startDate) {
        return new Period(startDate, null);
    }
}
