package com.daretodo.keyboardnotifier.groupbuy.infrastructure;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupBuyJpaRepository extends JpaRepository<GroupBuyEntity, Long> {

    Optional<GroupBuyEntity> findByProductId(Long productId);
}
