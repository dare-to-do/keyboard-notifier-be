package com.daretodo.keyboardnotifier.groupbuy.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class GroupBuy {
    private final Long id;
    private final Long productId;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final GroupBuyStatus status;
    private final List<GroupBuyParticipant> participants;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String createdBy;
    private final String updatedBy;

    @Builder
    public GroupBuy(Long id, Long productId, LocalDateTime startDateTime, LocalDateTime endDateTime,
                   GroupBuyStatus status, List<GroupBuyParticipant> participants,
                    LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy) {

        this.id = id;
        this.productId = productId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.status = status;
        this.participants = participants != null ? new ArrayList<>(participants) : new ArrayList<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public boolean canJoin() {
        if (status == GroupBuyStatus.NOT_YET) {
            return true;
        }
        if (endDateTime != null && LocalDateTime.now().isBefore(endDateTime)) {
            return true;
        }
        if (status == GroupBuyStatus.IN_PROGRESS) {
            return true;
        }
        return false;
    }

    public void addParticipant(GroupBuyParticipant participant) {
        if (!canJoin()) {
            throw new IllegalStateException("이미 종료된 공제이거나 시작되지 않은 공제입니다.");
        }
        if (isAlreadyJoined(participant.getUserId())) {
            throw new IllegalStateException("이미 참가된 공제입니다");
        }
        this.participants.add(participant);
    }

    private boolean isAlreadyJoined(Long userId) {
        return participants.stream()
                .anyMatch(p -> p.getUserId().equals(userId) && p.isActive());
    }

    public void validAlreadyParticipatedUser(Long userId) {
        if (isAlreadyJoined(userId)) {
            throw new IllegalStateException("이미 참가된 공제입니다");
        }
    }
}
