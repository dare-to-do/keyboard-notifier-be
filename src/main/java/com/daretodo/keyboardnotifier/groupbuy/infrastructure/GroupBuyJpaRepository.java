package com.daretodo.keyboardnotifier.groupbuy.infrastructure;

import com.daretodo.keyboardnotifier.groupbuy.domain.GroupBuy;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupBuyJpaRepository extends JpaRepository<GroupBuyEntity, Long> {

    Optional<GroupBuyEntity> findByProductId(Long productId);
}
