package com.daretodo.keyboardnotifier.product.controller;

public enum ProductSortBy {
    NEWEST("최신순"),
    OLDEST("오래된순"),
    LOW_PRICE("낮은 가격순"),
    HIGH_PRICE("높은 가격순");

    private String description;

    ProductSortBy(String description) {
        this.description = description;
    }
}
