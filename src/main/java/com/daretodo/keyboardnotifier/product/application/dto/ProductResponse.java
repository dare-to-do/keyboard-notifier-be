package com.daretodo.keyboardnotifier.product.application.dto;

import com.daretodo.keyboardnotifier.product.domain.ProductStatus;
import com.daretodo.keyboardnotifier.product.domain.ProductType;
import com.daretodo.keyboardnotifier.product.infrastructure.ProductEntity;

import java.time.LocalDateTime;
import java.util.List;

public record ProductResponse(
        String name,
        Long price,
        String unit,
        List<String> imageUrl,
        String productUrl,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate,
        ProductStatus productStatus,
        ProductType productType
) {

    public static ProductResponse fromEntity(ProductEntity productEntity) {
        List<String> imageUrls = List.of(productEntity.getImageUrl().split(","));
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
