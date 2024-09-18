package com.daretodo.keyboardnotifier.product.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Product {

    private Long id;
    private String name;
    private Long price;
    private String imageUrl;
    private ProductType productType;
    private String description;
    private Period period;
    private ProductStatus status;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;

    @Builder
    public Product(Long id, String name, Long price, String imageUrl, ProductType productType, String description, Period period, ProductStatus status,
        LocalDateTime createdAt, String createdBy, LocalDateTime updatedAt, String updatedBy) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.productType = productType;
        this.description = description;
        this.period = period;
        this.status = status;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }
}
