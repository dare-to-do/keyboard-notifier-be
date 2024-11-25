package com.daretodo.keyboardnotifier.product.application;

import com.daretodo.keyboardnotifier.product.controller.ProductSortBy;
import com.daretodo.keyboardnotifier.product.controller.ProductsRequestCondition;
import com.daretodo.keyboardnotifier.product.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void 상품을_생성한다() {
        // given
        var sut = new ProductService(productRepository);
        Product product1 = createProduct(1L, "Product A", 1000, PriceUnit.KRW, List.of("image1", "image2"), "productUrl1",
                ProductType.KEYBOARD, "description1", Period.from(LocalDateTime.parse("2024-11-25T00:00:00")), ProductStatus.IN_PROGRESS,
                LocalDateTime.now(), "admin", LocalDateTime.now(), "admin");
        Product product2 = createProduct(2L, "Product B", 2000, PriceUnit.KRW, List.of("image3", "image4"), "productUrl2",
                ProductType.KEYBOARD, "description2", Period.of(LocalDateTime.parse("2024-11-11T00:00:00"), LocalDateTime.parse("2024-11-30T00:00:00")), ProductStatus.IN_PROGRESS,
                LocalDateTime.now(), "admin", LocalDateTime.now(), "admin");
        List<Product> products = List.of(product1, product2);

        // when
        var result = sut.createProducts(products);

        // then
        assertEquals(2, result);
    }

    @Test
    void 전체_상품을_조회한다() {
        // given
        var sut = new ProductService(productRepository);
        Product product1 = createProduct(1L, "Product A", 1000, PriceUnit.KRW, List.of("image1", "image2"), "productUrl1",
                ProductType.KEYBOARD, "description1", Period.from(LocalDateTime.parse("2024-11-25T00:00:00")), ProductStatus.IN_PROGRESS,
                LocalDateTime.now(), "admin", LocalDateTime.now(), "admin");
        Product product2 = createProduct(2L, "Product B", 2000, PriceUnit.KRW, List.of("image3", "image4"), "productUrl2",
                ProductType.KEYBOARD, "description2", Period.of(LocalDateTime.parse("2024-11-11T00:00:00"), LocalDateTime.parse("2024-11-30T00:00:00")), ProductStatus.IN_PROGRESS,
                LocalDateTime.now(), "admin", LocalDateTime.now(), "admin");
        List<Product> products = List.of(product1, product2);
        productRepository.saveAll(products);
        ProductsRequestCondition condition = new ProductsRequestCondition(ProductStatus.IN_PROGRESS, ProductType.KEYBOARD, ProductSortBy.NEWEST, 2, 10);

        // when
        var result = sut.findAllProducts(condition);

        // then
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void 특정_상품을_조회한다() {
        // given
        var sut = new ProductService(productRepository);
        Product product = createProduct(1L, "Product A", 1000, PriceUnit.KRW, List.of("image1", "image2"), "productUrl1",
                ProductType.KEYBOARD, "description1", Period.from(LocalDateTime.parse("2024-11-25T00:00:00")), ProductStatus.IN_PROGRESS,
                LocalDateTime.now(), "admin", LocalDateTime.now(), "admin");
        List<Product> products = List.of(product);
        productRepository.saveAll(products);

        // when
        var result = sut.findProduct(1L);

        // then
        assertEquals(product.getName(), result.name());
        assertEquals(product.getPrice(), result.price());
    }

    private Product createProduct(Long id, String name, long price, PriceUnit unit, List<String> imageUrl,
                                  String productUrl, ProductType productType, String description, Period period,
                                  ProductStatus status, LocalDateTime createdAt, String createdBy,
                                  LocalDateTime updatedAt, String updatedBy) {
        return Product.builder()
                .id(id).name(name).price(price).unit(unit)
                .imageUrl(imageUrl).productUrl(productUrl).productType(productType).description(description)
                .period(period).status(status).createdAt(createdAt).createdBy(createdBy)
                .updatedAt(updatedAt).updatedBy(updatedBy).build();


    }
}