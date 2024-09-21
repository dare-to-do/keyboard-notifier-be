package com.daretodo.keyboardnotifier.product.domain;

import java.time.LocalDateTime;

public record Period(
    LocalDateTime startDate,
    LocalDateTime endDate
) {

    public static Period of(LocalDateTime startDate, LocalDateTime endDate) {
        return new Period(startDate, endDate);
    }

    public static Period from(LocalDateTime startDate) {
        return new Period(startDate, null);
    }
}
