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
    private final int minParticipants;
    private final int maxParticipants;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final GroupBuyStatus status;
    private List<GroupBuyParticipant> participants;

    @Builder
    public GroupBuy(Long id, Long productId, int minParticipants, int maxParticipants, LocalDateTime startDateTime, LocalDateTime endDateTime,
                   GroupBuyStatus status) {
        this.id = id;
        this.productId = productId;
        this.minParticipants = minParticipants;
        this.maxParticipants = maxParticipants;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.status = status;
        this.participants = new ArrayList<>();
    }

    public boolean canJoin() {
        return status == GroupBuyStatus.ACTIVE && 
               getActiveParticipantCount() < maxParticipants &&
               LocalDateTime.now().isBefore(endDateTime);
    }

    public void addParticipant(GroupBuyParticipant participant) {
        if (!canJoin()) {
            throw new IllegalStateException("Cannot join this group buy");
        }
        if (isAlreadyJoined(participant.getUserId())) {
            throw new IllegalStateException("User has already joined this group buy");
        }
        this.participants.add(participant);
    }

    public boolean isSuccessful() {
        return getActiveParticipantCount() >= minParticipants;
    }

    public int getActiveParticipantCount() {
        return (int) participants.stream()
                .filter(GroupBuyParticipant::isActive)
                .count();
    }

    private boolean isAlreadyJoined(Long userId) {
        return participants.stream()
                .anyMatch(p -> p.getUserId().equals(userId) && p.isActive());
    }
}
