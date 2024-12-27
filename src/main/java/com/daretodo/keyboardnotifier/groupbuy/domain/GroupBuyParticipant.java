package com.daretodo.keyboardnotifier.groupbuy.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GroupBuyParticipant {
    private final Long id;
    private final Long groupBuyId;
    private final Long userId;
    private final LocalDateTime joinedAt;
    private GroupBuyParticipantStatus status;

    @Builder
    public GroupBuyParticipant(Long id, Long groupBuyId, Long userId, 
                              LocalDateTime joinedAt, GroupBuyParticipantStatus status) {
        this.id = id;
        this.groupBuyId = groupBuyId;
        this.userId = userId;
        this.joinedAt = joinedAt;
        this.status = status;
    }

    public void cancel() {
        this.status = GroupBuyParticipantStatus.CANCELLED;
    }

    public boolean isActive() {
        return this.status == GroupBuyParticipantStatus.ACTIVE;
    }
}
