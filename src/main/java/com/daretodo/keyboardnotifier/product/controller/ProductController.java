package com.daretodo.keyboardnotifier.product.controller;

import com.daretodo.keyboardnotifier.common.GiBiResponseBody;
import com.daretodo.keyboardnotifier.common.PageableOutput;
import com.daretodo.keyboardnotifier.product.application.ProductResponse;
import com.daretodo.keyboardnotifier.product.application.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 다건 등록")
    @PostMapping("/products")
    public GiBiResponseBody<Integer> createProduct(@RequestBody ProductCreateRequest requests) {
        var createdCount = productService.createProducts(requests.toProducts());
        return GiBiResponseBody.success(createdCount);
    }

    @Operation(summary = "상품 전체 조회")
    @GetMapping("/products")
    public GiBiResponseBody<PageableOutput<ProductResponse>> findAllProducts(ProductsRequestCondition condition) {
        var products = productService.findAllProducts(condition.getProductStatus(), condition.getProductType(), condition.getPageable(), condition.getSortBy());
        return GiBiResponseBody.success(new PageableOutput<>(products));
    }
}
