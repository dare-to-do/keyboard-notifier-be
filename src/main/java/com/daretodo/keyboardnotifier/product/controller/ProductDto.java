package com.daretodo.keyboardnotifier.product.controller;

import com.daretodo.keyboardnotifier.product.domain.Period;
import com.daretodo.keyboardnotifier.product.domain.Product;
import com.daretodo.keyboardnotifier.product.domain.ProductStatus;
import com.daretodo.keyboardnotifier.product.domain.ProductType;

import java.time.LocalDateTime;

import org.springframework.util.Assert;

public record ProductDto(
        String name,
        Long price,
        String unit,
        String imageUrl,
        String productUrl,
        ProductType productType,
        ProductStatus productStatus,
        String description,
        LocalDateTime startDate,
        LocalDateTime endDate
) {

    public Product toProduct() {

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("시작일은 종료일보다 이전이어야 합니다.");
        }

        String[] imageUrls = imageUrl.split(",");

        Assert.hasText(name, "상품명은 필수입니다.");
        Assert.notNull(price, "가격은 필수입니다.");
        Assert.hasText(imageUrls[0], "이미지 URL은 필수입니다.");
        Assert.hasText(productUrl, "상품 URL은 필수입니다.");
        Assert.notNull(productType, "상품 종류는 필수입니다.");

        return new Product(
                null,
                name,
                price,
                unit,
                imageUrl,
                imageUrls,
                productUrl,
                productType,
                description,
                Period.of(startDate, endDate),
                productStatus,
                null,
                null,
                null,
                null
        );
    }
}
