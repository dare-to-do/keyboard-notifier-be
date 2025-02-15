package com.daretodo.keyboardnotifier.product.application;

import com.daretodo.keyboardnotifier.product.application.dto.ProductResponse;
import com.daretodo.keyboardnotifier.product.controller.ProductSortBy;
import com.daretodo.keyboardnotifier.product.domain.Product;
import com.daretodo.keyboardnotifier.product.domain.ProductRepository;
import com.daretodo.keyboardnotifier.product.domain.ProductStatus;
import com.daretodo.keyboardnotifier.product.domain.ProductType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductEventService productEventService;
    private final ProductMapper productMapper;

    @Transactional
    public Integer createProducts(List<Product> products) {
        return productRepository.saveAll(products).size();
    }

    public Page<ProductResponse> findAllProducts(
        ProductStatus productStatus,
        ProductType productType,
        Pageable pageable,
        ProductSortBy sortBy
    ) {
        Page<Product> products = productRepository.findAllProducts(productStatus, productType, pageable, sortBy);
        return productMapper.toProductResponsePage(products);
    }

    public ProductResponse findProduct(Long id) {
        Product product = productRepository.findById(id);
        productEventService.publishReadEvent(product);
        return productMapper.toProductResponse(product);
    }

    public List<ProductResponse> findSimilarProducts(Long id) {
        List<Product> similarProducts = productRepository.findSimilarProducts(id);
        return productMapper.toProductResponseList(similarProducts);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId);
        productRepository.delete(product);
    }
}
