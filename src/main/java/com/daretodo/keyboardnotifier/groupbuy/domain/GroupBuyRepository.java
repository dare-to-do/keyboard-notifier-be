package com.daretodo.keyboardnotifier.groupbuy.domain;

import java.util.List;

public interface GroupBuyRepository {
    GroupBuy save(GroupBuy groupBuy);
    GroupBuy findByProductId(Long productId);

    List<GroupBuy> saveAll(List<GroupBuy> groupBuys);
}
