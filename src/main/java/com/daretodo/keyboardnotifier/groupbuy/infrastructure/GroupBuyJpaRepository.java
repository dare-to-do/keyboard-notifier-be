package com.daretodo.keyboardnotifier.groupbuy.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

public interface GroupBuyJpaRepository extends JpaRepository<GroupBuyEntity, Long> {

    Optional<GroupBuyEntity> findByProductId(Long productId);

    Stream<GroupBuyEntity> findAllByStartDateTimeBetween(LocalDateTime from, LocalDateTime to);
}
