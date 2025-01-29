package com.daretodo.keyboardnotifier.groupbuy.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GroupBuyNotification {
    private final Long id;

    private final Long groupBuyId;

    private final Long receiverId;

    private GroupBuyNotificationStatus status;

    private final LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private final String createdBy;

    private String updatedBy;

    @Builder
    public GroupBuyNotification(Long id, Long groupBuyId, Long receiverId, GroupBuyNotificationStatus status,
                                LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy) {
        this.id = id;
        this.groupBuyId = groupBuyId;
        this.receiverId = receiverId;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public void updateStatus(GroupBuyNotificationStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }
}
