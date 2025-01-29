package com.daretodo.keyboardnotifier.groupbuy.infrastructure;

import com.daretodo.keyboardnotifier.common.BaseTimeEntity;
import com.daretodo.keyboardnotifier.groupbuy.domain.GroupBuy;
import com.daretodo.keyboardnotifier.groupbuy.domain.GroupBuyStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "group_buys")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupBuyEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "start_date_time")
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time")
    private LocalDateTime endDateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GroupBuyStatus status;

    @OneToMany(mappedBy = "groupBuyId", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "group_buy_participants")
    private List<GroupBuyParticipantEntity> groupBuyParticipants;

    public static GroupBuyEntity from(GroupBuy groupBuy) {
        GroupBuyEntity entity = new GroupBuyEntity();
        entity.id = groupBuy.getId();
        entity.productId = groupBuy.getProductId();
        entity.startDateTime = groupBuy.getStartDateTime();
        entity.endDateTime = groupBuy.getEndDateTime();
        entity.status = groupBuy.getStatus();
        entity.groupBuyParticipants = groupBuy.getParticipants().stream()
                .map(GroupBuyParticipantEntity::from)
                .toList();
        entity.createdAt = groupBuy.getCreatedAt();
        entity.updatedAt = groupBuy.getUpdatedAt();
        entity.createdBy = groupBuy.getCreatedBy();
        entity.updatedBy = groupBuy.getUpdatedBy();
        return entity;
    }

    public GroupBuy toDomain() {
        return GroupBuy.builder()
            .id(id)
            .productId(productId)
            .startDateTime(startDateTime)
            .endDateTime(endDateTime)
            .status(status)
            .participants(groupBuyParticipants.stream()
                .map(GroupBuyParticipantEntity::toDomain).toList())
            .createdAt(createdAt)
            .updatedAt(updatedAt)
            .createdBy(createdBy)
            .updatedBy(updatedBy)
            .build();
    }
}
