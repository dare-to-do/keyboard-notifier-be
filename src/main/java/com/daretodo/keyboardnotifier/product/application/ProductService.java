package com.daretodo.keyboardnotifier.product.application;

import com.daretodo.keyboardnotifier.product.controller.ProductSortBy;
import com.daretodo.keyboardnotifier.product.domain.Product;
import com.daretodo.keyboardnotifier.product.domain.ProductStatus;
import com.daretodo.keyboardnotifier.product.domain.ProductType;
import com.daretodo.keyboardnotifier.product.infrastructure.ProductEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Integer createProducts(List<Product> products) {
        return productRepository.saveAll(products);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> findAllProducts(ProductStatus productStatus, ProductType productType, Pageable pageable, ProductSortBy sortBy) {
        Page<ProductEntity> productEntities = productRepository.findAllProducts(productStatus, productType, pageable, sortBy);
        return productEntities.map(ProductResponse::fromEntity);
    }
}
