package com.daretodo.keyboardnotifier.groupbuy.infrastructure;

import com.daretodo.keyboardnotifier.groupbuy.domain.GroupBuy;
import com.daretodo.keyboardnotifier.groupbuy.domain.GroupBuyStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "group_buys")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupBuyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "min_participants")
    private int minParticipants;

    @Column(name = "max_participants")
    private int maxParticipants;

    @Column(name = "start_date_time")
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time")
    private LocalDateTime endDateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GroupBuyStatus status;

    @Column(name = "current_participants")
    private int currentParticipants;

    public static GroupBuyEntity from(GroupBuy groupBuy) {
        GroupBuyEntity entity = new GroupBuyEntity();
        entity.id = groupBuy.getId();
        entity.productId = groupBuy.getProductId();
        entity.minParticipants = groupBuy.getMinParticipants();
        entity.maxParticipants = groupBuy.getMaxParticipants();
        entity.startDateTime = groupBuy.getStartDateTime();
        entity.endDateTime = groupBuy.getEndDateTime();
        entity.status = groupBuy.getStatus();
        entity.currentParticipants = groupBuy.getParticipants();
        return entity;
    }

    public GroupBuy toDomain() {
        return GroupBuy.builder()
                .id(id)
                .productId(productId)
                .minParticipants(minParticipants)
                .maxParticipants(maxParticipants)
                .startDateTime(startDateTime)
                .endDateTime(endDateTime)
                .status(status)
                .currentParticipants(currentParticipants)
                .build();
    }
}
