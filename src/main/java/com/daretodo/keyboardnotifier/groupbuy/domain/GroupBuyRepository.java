package com.daretodo.keyboardnotifier.groupbuy.domain;

public interface GroupBuyRepository {
    GroupBuy save(GroupBuy groupBuy);
    GroupBuy findByProductId(Long productId);
}
