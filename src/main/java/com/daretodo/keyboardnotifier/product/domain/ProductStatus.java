package com.daretodo.keyboardnotifier.product.domain;

import lombok.Getter;

@Getter
public enum ProductStatus {
    PENDING("예정"),
    IN_PROGRESS("진행"),
    COMPLETED("완료"),
    FAILED("실패"),
    CANCELLED("취소"),
    DELETED("삭제"),
    UNKNOWN("알수없음");

    private final String description;

    ProductStatus(String description) {
        this.description = description;
    }
}
