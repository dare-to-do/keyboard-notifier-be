package com.daretodo.keyboardnotifier.product.controller;

import com.daretodo.keyboardnotifier.common.PageRequestCondition;
import com.daretodo.keyboardnotifier.product.domain.ProductStatus;
import com.daretodo.keyboardnotifier.product.domain.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ProductsRequestCondition extends PageRequestCondition {

    @Schema(description = "공제 상태")
    private ProductStatus productStatus;

    @Schema(description = "제품 카테고리")
    private ProductType productType;

    @Schema(description = "정렬 기준")
    private ProductSortBy sortBy = ProductSortBy.NEWEST;

    @Schema(description = "시작 페이지", defaultValue = "1")
    private Integer page = 1;

    @Schema(description = "페이지 사이즈", defaultValue = "10")
    private Integer size = 10;
}
