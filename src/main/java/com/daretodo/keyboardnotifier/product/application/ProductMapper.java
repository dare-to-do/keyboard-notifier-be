package com.daretodo.keyboardnotifier.product.application;

import com.daretodo.keyboardnotifier.product.application.dto.ProductResponse;
import com.daretodo.keyboardnotifier.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {

    public Page<ProductResponse> toProductResponsePage(Page<Product> products) {
        return products.map(this::toProductResponse);
    }

    public ProductResponse toProductResponse(Product product) {
        return ProductResponse.fromDomain(product);
    }

    public List<ProductResponse> toProductResponseList(List<Product> products) {
        return products.stream().map(this::toProductResponse).toList();
    }
}
