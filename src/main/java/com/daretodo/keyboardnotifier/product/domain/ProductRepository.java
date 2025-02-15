package com.daretodo.keyboardnotifier.product.domain;

import com.daretodo.keyboardnotifier.product.controller.ProductSortBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepository {

    List<Product> saveAll(List<Product> products);

    Page<Product> findAllProducts(ProductStatus productStatus, ProductType productType,
                                        Pageable pageable, ProductSortBy sortBy);

    Product findById(Long id);

    List<Product> findSimilarProducts(Long id);

    void increaseViewCount(Product product);

    void save(Product product);

    void delete(Product product);
}
