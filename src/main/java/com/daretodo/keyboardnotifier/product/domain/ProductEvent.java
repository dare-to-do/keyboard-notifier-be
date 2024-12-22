package com.daretodo.keyboardnotifier.product.domain;

import lombok.Getter;

@Getter
public class ProductEvent {
    private final Product product;
    private final EventType eventType;

    private ProductEvent(Product product, EventType eventType) {
        this.product = product;
        this.eventType = eventType;
    }

    public static ProductEvent of(Product product, EventType type) {
        return new ProductEvent(product, type);
    }

    public enum EventType {
        READ,
    }


}
