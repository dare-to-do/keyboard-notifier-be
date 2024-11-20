package com.daretodo.keyboardnotifier.product.application;

import com.daretodo.keyboardnotifier.product.domain.ProductStatus;
import com.daretodo.keyboardnotifier.product.domain.ProductType;
import com.daretodo.keyboardnotifier.product.infrastructure.ProductEntity;

import java.time.LocalDateTime;

public record ProductResponse(
        String name,
        Long price,
        String unit,
        String[] imageUrl,
        String productUrl,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        ProductStatus status,
        ProductType type
) {

    public static ProductResponse fromEntity(ProductEntity productEntity) {
        String[] imageUrls = productEntity.getImageUrl().split(",");
        return new ProductResponse(
                productEntity.getName(),
                productEntity.getPrice(),
                productEntity.getPriceUnit().getKoreanName(),
                imageUrls,
                productEntity.getProductUrl(),
                productEntity.getDescription(),
                productEntity.getStartDate(),
                productEntity.getEndDate(),
                productEntity.getStatus(),
                productEntity.getType()
        );
    }
}
