package com.daretodo.keyboardnotifier.groupbuy.domain;

import java.time.LocalDateTime;
import java.util.List;

public interface GroupBuyRepository {

    GroupBuy save(GroupBuy groupBuy);

    GroupBuy findByProductId(Long productId);

    List<GroupBuy> findAllByStartDateTimeBetween(LocalDateTime localDateTime, LocalDateTime today);
}
