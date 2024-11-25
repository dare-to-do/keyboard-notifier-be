package com.daretodo.keyboardnotifier.product.application;


import com.daretodo.keyboardnotifier.product.controller.ProductController;
import com.daretodo.keyboardnotifier.product.controller.dto.ProductCreateRequest;
import com.daretodo.keyboardnotifier.product.controller.ProductDto;
import com.daretodo.keyboardnotifier.product.controller.dto.ProductsRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductControllerTest {
    @Autowired
    private ProductService productService;

    @Test
    void 상품을_다건_등록한다() {
        // given
        var sut = new ProductController(productService);
        List<ProductDto> products = List.of(
                new ProductDto("Product A", 1000L, "원", "image1, image2", "productUrl1",
                        "KEYBOARD", "description1",
                        LocalDateTime.parse("2024-11-11T00:00:00"), LocalDateTime.parse("2024-11-25T00:00:00")),
                new ProductDto("Product B", 2000L, "원", "image3, image4", "productUrl2",
                        "KEYBOARD", "description2",
                        LocalDateTime.parse("2024-11-11T00:00:00"), LocalDateTime.parse("2024-11-25T00:00:00"))
        );
        ProductCreateRequest request = new ProductCreateRequest(products);

        // when
        var result = sut.createProduct(request);

        // then
        assertThat(result.getCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getMessage()).isEqualTo("정상 처리되었습니다.");
        assertThat(result.getData()).isEqualTo(2);
    }

    @Test
    void 상품을_전체_조회한다() {
        // given
        var sut = new ProductController(productService);
        ProductsRequest request = new ProductsRequest("IN_PROGRESS", "KEYBOARD", "NEWEST", 1, 10);

        // when
        var result = sut.findAllProducts(request);

        // then
        assertThat(result.getCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getMessage()).isEqualTo("정상 처리되었습니다.");
    }

    @Test
    void 특정_상품을_조회한다() {
        // given
        var sut = new ProductController(productService);
        sut.createProduct(new ProductCreateRequest(List.of(
                new ProductDto("Product A", 1000L, "원", "image1, image2", "productUrl1",
                        "KEYBOARD", "description1",
                        LocalDateTime.parse("2024-11-11T00:00:00"), LocalDateTime.parse("2024-11-25T00:00:00"))
        )));
        Long id = 1L;

        // when
        var result = sut.findProduct(id);

        // then
        assertThat(result.getCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(result.getMessage()).isEqualTo("정상 처리되었습니다.");
    }
}
