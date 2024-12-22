package com.daretodo.keyboardnotifier.product.application;

import com.daretodo.keyboardnotifier.product.application.dto.ProductResponse;
import com.daretodo.keyboardnotifier.product.controller.ProductSortBy;
import com.daretodo.keyboardnotifier.product.domain.Product;
import com.daretodo.keyboardnotifier.product.domain.ProductStatus;
import com.daretodo.keyboardnotifier.product.domain.ProductType;
import com.daretodo.keyboardnotifier.product.infrastructure.ProductEntity;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private static final int MAX_RETRY_COUNT = 5;

    @Transactional
    public Integer createProducts(List<Product> products) {
        return productRepository.saveAll(products);
    }

    public Page<ProductResponse> findAllProducts(ProductStatus productStatus, ProductType productType,
                                                 Pageable pageable, ProductSortBy sortBy) {
        Page<ProductEntity> productEntities = productRepository
                .findAllProducts(productStatus, productType, pageable, sortBy);
        return productEntities.map(ProductResponse::fromEntity);
    }

    public ProductResponse findProduct(Long id) {
        int retryCount = 0;

        while (retryCount <= MAX_RETRY_COUNT) {
            try {
                ProductEntity productEntity = productRepository.findById(id);
                productRepository.updateViewCount(id);
                return ProductResponse.fromEntity(productEntity);
            } catch (OptimisticLockingFailureException e) {
                retryBackOff(retryCount++);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("상품을 찾을 수 없습니다.");
            }
        }

    public List<ProductResponse> findSimilarProducts(Long id) {
        List<ProductEntity> similarProducts = productRepository.findSimilarProducts(id);
        return similarProducts.stream().map(ProductResponse::fromEntity).toList();
    }


    private void retryBackOff(int retryCount) {
        if (retryCount == MAX_RETRY_COUNT) {
            throw new RuntimeException("조회수 업데이트에 실패했습니다.");
        }

        long backoffTime = (long) Math.pow(2, retryCount);
        try {
            Thread.sleep(backoffTime);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

}
