package com.daretodo.keyboardnotifier.groupbuy.infrastructure;

import com.daretodo.keyboardnotifier.common.BaseTimeEntity;
import com.daretodo.keyboardnotifier.groupbuy.domain.GroupBuyNotification;
import com.daretodo.keyboardnotifier.groupbuy.domain.GroupBuyNotificationStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "group_buy_notifications")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupBuyNotificationEntity extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "group_buy_id", nullable = false)
    private Long groupBuyId;

    @Column(name= "user_id", nullable = false)
    private Long receiverId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private GroupBuyNotificationStatus status;

    public static GroupBuyNotificationEntity from(GroupBuyNotification notification) {
        GroupBuyNotificationEntity entity = new GroupBuyNotificationEntity();
        entity.id = notification.getId();
        entity.groupBuyId = notification.getGroupBuyId();
        entity.receiverId = notification.getReceiverId();
        entity.status = notification.getStatus();
        entity.createdAt = notification.getCreatedAt();
        entity.createdBy = notification.getCreatedBy();
        entity.updatedAt = notification.getUpdatedAt();
        entity.updatedBy = notification.getUpdatedBy();
        return entity;
    }

    public GroupBuyNotification toDomain() {
        return GroupBuyNotification.builder()
                .id(id)
                .groupBuyId(groupBuyId)
                .receiverId(receiverId)
                .status(status)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .createdBy(createdBy)
                .updatedBy(updatedBy)
                .build();
    }

    public void updateStatus(GroupBuyNotificationStatus groupBuyNotificationStatus) {
        this.status = groupBuyNotificationStatus;
        this.updatedAt = LocalDateTime.now();
    }
}
