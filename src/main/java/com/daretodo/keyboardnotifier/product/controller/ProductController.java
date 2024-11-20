package com.daretodo.keyboardnotifier.product.controller;

import com.daretodo.keyboardnotifier.common.GiBiResponseBody;
import com.daretodo.keyboardnotifier.common.PageableOutput;
import com.daretodo.keyboardnotifier.product.application.ProductResponse;
import com.daretodo.keyboardnotifier.product.application.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 다건 등록")
    @PostMapping
    public SokeyResponseBody<Integer> createProduct(@RequestBody ProductCreateRequest requests) {
        var createdCount = productService.createProducts(requests.toProducts());
        return GiBiResponseBody.success(createdCount);
    }

    @Operation(summary = "상품 전체 조회")
    @GetMapping
    public SokeyResponseBody<PageableOutput<ProductResponse>> findAllProducts(ProductsRequestCondition condition) {
        var products = productService.findAllProducts(condition.getProductStatus(), condition.getProductType(), condition.getPageable(), condition.getSortBy());
        return GiBiResponseBody.success(new PageableOutput<>(products));
    }

    @Operation(summary = "특정 상품 조회")
    @GetMapping("/{id}")
    public SokeyResponseBody<ProductResponse> findProduct(@PathVariable Long id) {
        var product = productService.findProduct(id);
        return SokeyResponseBody.success(product);
    }

}
