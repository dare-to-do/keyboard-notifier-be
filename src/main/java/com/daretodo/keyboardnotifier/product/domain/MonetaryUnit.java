package com.daretodo.keyboardnotifier.product.domain;

import lombok.Getter;

@Getter
public enum MonetaryUnit {
    KRW("원", "₩"),
    USD("달러", "$"),
    EUR("유로", "€"),
    JPY("엔", "¥"),
    CNY("위안", "¥"),
    GBP("파운드", "£"),
    UNKNOWN("알수없음", "");

    private final String koreanName;
    private final String symbol;

    MonetaryUnit(String koreanName, String symbol) {
        this.koreanName = koreanName;
        this.symbol = symbol;
    }

    public static MonetaryUnit from(String value) {
        for (MonetaryUnit unit : values()) {
            if (unit.name().equals(value) || unit.koreanName.equals(value) || unit.symbol.equals(value)) {
                return unit;
            }
        }
        return UNKNOWN;
    }
}
