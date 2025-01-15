package com.daretodo.keyboardnotifier.product.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Getter
public class Product {

    private Long id;
    private String name;
    private Long price;
    private PriceUnit priceUnit;
    private List<String> imageUrl;
    private String productUrl;
    private ProductType type;
    private String description;
    private Optional<Period> period;
    private ProductStatus status;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;
    private ViewCount viewCount;

    @Builder
    public Product(Long id, String name, Long price, PriceUnit priceUnit, List<String> imageUrl, String productUrl,
                   ProductType type, String description, Period period, ProductStatus status, LocalDateTime createdAt,
                   String createdBy, LocalDateTime updatedAt, String updatedBy, ViewCount viewCount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.priceUnit = priceUnit;
        this.imageUrl = imageUrl;
        this.productUrl = productUrl;
        this.type = type;
        this.description = description;
        this.period = Optional.ofNullable(period);
        this.status = status;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
        this.viewCount = viewCount;
    }

    public void delete() {
        this.status = ProductStatus.DELETED;
    }
}
