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

    @Column(name = "message_id")
    private String messageId;

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
        entity.messageId = notification.getMessageId();
        entity.groupBuyId = notification.getGroupBuyId();
        entity.receiverId = notification.getReceiverId();
        entity.status = notification.getStatus();
        entity.createdAt = notification.getCreatedAt() != null ? notification.getCreatedAt() : LocalDateTime.now();
        entity.createdBy = notification.getCreatedBy() != null ? notification.getCreatedBy() : "SYSTEM";
        entity.updatedAt = notification.getUpdatedAt() != null ? notification.getUpdatedAt() : LocalDateTime.now();
        entity.updatedBy = notification.getUpdatedBy() != null ? notification.getUpdatedBy() : "SYSTEM";
        return entity;
    }

    public GroupBuyNotification toDomain() {
        return GroupBuyNotification.builder()
                .id(id)
                .messageId(messageId)
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
