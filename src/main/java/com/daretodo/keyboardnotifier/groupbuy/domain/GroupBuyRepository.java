package com.daretodo.keyboardnotifier.groupbuy.domain;

import java.util.List;
import java.util.Optional;

public interface GroupBuyRepository {
    GroupBuy save(GroupBuy groupBuy);
    Optional<GroupBuy> findById(Long id);
    List<GroupBuy> findByProductId(Long productId);
    List<GroupBuy> findActiveGroupBuys();
}
