package com.daretodo.keyboardnotifier.product.domain;

import lombok.Getter;

@Getter
public enum ProductStatus {
    NOT_YET("예정", "NOT YET"),
    IN_PROGRESS("진행중", "IN PROGRESS"),
    DONE("종료", "DONE");

    private final String description;
    private final String value;

    ProductStatus(String description, String value) {
        this.description = description;
        this.value = value;
    }
}
