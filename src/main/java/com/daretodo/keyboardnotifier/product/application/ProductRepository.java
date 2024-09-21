package com.daretodo.keyboardnotifier.product.application;

import com.daretodo.keyboardnotifier.product.controller.ProductSortBy;
import com.daretodo.keyboardnotifier.product.domain.Product;
import com.daretodo.keyboardnotifier.product.domain.ProductStatus;
import com.daretodo.keyboardnotifier.product.domain.ProductType;
import com.daretodo.keyboardnotifier.product.infrastructure.ProductEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepository {

    Integer saveAll(List<Product> products);

    Page<ProductEntity> findAllProducts(ProductStatus productStatus, ProductType productType, Pageable pageable, ProductSortBy sortBy);
}
