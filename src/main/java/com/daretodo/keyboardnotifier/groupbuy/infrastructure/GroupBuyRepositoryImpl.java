package com.daretodo.keyboardnotifier.groupbuy.infrastructure;

import com.daretodo.keyboardnotifier.groupbuy.domain.GroupBuy;
import com.daretodo.keyboardnotifier.groupbuy.domain.GroupBuyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroupBuyRepositoryImpl implements GroupBuyRepository {

    private final GroupBuyJpaRepository groupBuyJpaRepository;

    @Override
    public GroupBuy save(GroupBuy groupBuy) {
        return groupBuyJpaRepository.save(GroupBuyEntity.from(groupBuy)).toDomain();
    }

    @Override
    public GroupBuy findByProductId(Long productId) {
        return groupBuyJpaRepository.findByProductId(productId).map(GroupBuyEntity::toDomain)
            .orElseThrow(() -> new IllegalArgumentException("해당 상품의 공동구매가 존재하지 않습니다."));
    }
}
