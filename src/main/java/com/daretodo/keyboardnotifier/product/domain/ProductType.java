package com.daretodo.keyboardnotifier.product.domain;

public enum ProductType {
    KIT("키트"),
    KEYBOARD("키보드"),
    SWITCH("스위치"),
    KEYCAP("키캡"),
    STABILIZER("스타빌라이저"),
    PARTS("부품"),
    ETC("기타");

    private final String koreanName;

    ProductType(String koreanName) {
        this.koreanName = koreanName;
    }

    public static ProductType from(String value) {
        for (ProductType type : values()) {
            if (type.koreanName.equals(value) || type.name().equals(value)) {
                return type;
            }
        }
        return ETC;
    }
}
