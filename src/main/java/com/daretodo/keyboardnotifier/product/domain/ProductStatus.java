package com.daretodo.keyboardnotifier.product.domain;

import lombok.Getter;

@Getter
public enum ProductStatus {
    NOT_YET("예정"),
    IN_PROGRESS("진행중"),
    DONE("종료"),
    UNKNOWN("알수없음");

    private final String description;

    ProductStatus(String description) {
        this.description = description;
    }
}
