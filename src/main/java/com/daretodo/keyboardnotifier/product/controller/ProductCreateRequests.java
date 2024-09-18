package com.daretodo.keyboardnotifier.product.controller;

import com.daretodo.keyboardnotifier.product.domain.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record ProductCreateRequests(
    @Schema(description = "등록할 상품 리스트")
    List<ProductCreateRequest> products
) {

    public List<Product> toProducts() {
        return products.stream()
            .map(ProductCreateRequest::toProduct)
            .toList();
    }
}
