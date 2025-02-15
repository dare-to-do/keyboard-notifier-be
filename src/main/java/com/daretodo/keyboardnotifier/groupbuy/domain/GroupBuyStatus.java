package com.daretodo.keyboardnotifier.groupbuy.domain;

import com.daretodo.keyboardnotifier.product.domain.ProductStatus;
import lombok.Getter;

@Getter
public enum GroupBuyStatus {
    PENDING("예정"),
    IN_PROGRESS("진행"),
    COMPLETED("완료"),
    FAILED("실패"),
    CANCELLED("취소"),
    DELETED("삭제"),
    UNKNOWN("알수없음");

    private final String description;

    GroupBuyStatus(String description) {
        this.description = description;
    }

    GroupBuyStatus from(ProductStatus productStatus) {
        return GroupBuyStatus.valueOf(productStatus.name());
    }
}
