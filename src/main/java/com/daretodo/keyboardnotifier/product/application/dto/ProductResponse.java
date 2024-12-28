package com.daretodo.keyboardnotifier.product.application.dto;

import com.daretodo.keyboardnotifier.product.domain.Product;
import com.daretodo.keyboardnotifier.product.domain.ProductStatus;
import com.daretodo.keyboardnotifier.product.domain.ProductType;

import java.time.LocalDateTime;
import java.util.List;

public record ProductResponse(
        Long id,
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

    public static ProductResponse fromDomain(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getPriceUnit().getKoreanName(),
                product.getImageUrl(),
                product.getProductUrl(),
                product.getDescription(),
                product.getPeriod().isEmpty() ? null : product.getPeriod().get().getStartDate(),
                product.getPeriod().isEmpty() ? null : product.getPeriod().get().getEndDate(),
                product.getStatus(),
                product.getType()
        );
    }
}
