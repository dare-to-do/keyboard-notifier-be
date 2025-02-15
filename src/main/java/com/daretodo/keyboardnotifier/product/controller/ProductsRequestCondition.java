package com.daretodo.keyboardnotifier.product.controller;

import com.daretodo.keyboardnotifier.common.PageRequestCondition;
import com.daretodo.keyboardnotifier.product.domain.ProductStatus;
import com.daretodo.keyboardnotifier.product.domain.ProductType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Data
@EqualsAndHashCode(callSuper = true)
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

    @Override
    @JsonIgnore
    public Pageable getPageable() {
        return PageRequest.of(page - 1, size);
    }
}
