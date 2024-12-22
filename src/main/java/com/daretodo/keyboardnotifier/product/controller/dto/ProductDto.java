package com.daretodo.keyboardnotifier.product.controller.dto;

import com.daretodo.keyboardnotifier.product.domain.*;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public record ProductDto(
        String name,
        Long price,
        String unit,
        String imageUrl,
        String productUrl,
        String productType,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate
) {

    public Product toProduct() {

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작일은 종료일보다 이전이어야 합니다.");
        }

        List<String> imageUrls = Arrays.asList(imageUrl.split(","));

        Assert.hasText(name, "상품명은 필수입니다.");
        Assert.notNull(price, "가격은 필수입니다.");
        Assert.notNull(imageUrls, "이미지 URL은 필수입니다.");
        Assert.hasText(productUrl, "상품 URL은 필수입니다.");
        Assert.notNull(productType, "상품 종류는 필수입니다.");

        return new Product(
                null,
                name,
                price,
                PriceUnit.from(unit),
                imageUrls,
                productUrl,
                ProductType.from(productType),
                description,
                Period.of(startDate, endDate),
                getProductStatus(startDate, endDate),
                null,
                null,
                null,
                null,
                null
        );
    }

    private ProductStatus getProductStatus(LocalDateTime startDate, LocalDateTime endDate) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(startDate)) {
            return ProductStatus.NOT_YET;
        }

        if (now.isAfter(endDate)) {
            return ProductStatus.DONE;
        }

        return ProductStatus.IN_PROGRESS;
    }
}
