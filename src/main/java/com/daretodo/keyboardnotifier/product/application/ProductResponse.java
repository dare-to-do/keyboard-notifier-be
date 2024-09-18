package com.daretodo.keyboardnotifier.product.application;

import com.daretodo.keyboardnotifier.product.domain.ProductStatus;
import com.daretodo.keyboardnotifier.product.domain.ProductType;
import com.daretodo.keyboardnotifier.product.infrastructure.ProductEntity;
import java.time.LocalDateTime;

public record ProductResponse(
    String name,
    Long price,
    String imageUrl,
    String description,
    LocalDateTime startDate,
    LocalDateTime endDate,
    ProductStatus status,
    ProductType type
) {

    public static ProductResponse fromEntity(ProductEntity productEntity) {
        return new ProductResponse(
            productEntity.getName(),
            productEntity.getPrice(),
            productEntity.getImageUrl(),
            productEntity.getDescription(),
            productEntity.getStartDate(),
            productEntity.getEndDate(),
            productEntity.getStatus(),
            productEntity.getType()
        );
    }
}
