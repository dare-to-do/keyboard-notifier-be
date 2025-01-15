package com.daretodo.keyboardnotifier.groupbuy.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GroupBuy {
    private final Long id;
    private final Long productId;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final GroupBuyStatus status;
    private List<GroupBuyParticipant> participants;

    @Builder
    public GroupBuy(Long id, Long productId, LocalDateTime startDateTime, LocalDateTime endDateTime,
                   GroupBuyStatus status, List<GroupBuyParticipant> participants) {
        this.id = id;
        this.productId = productId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.status = status;
        this.participants = participants;
    }

    public boolean canJoin() {
        return status == GroupBuyStatus.ACTIVE &&
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

    private boolean isAlreadyJoined(Long userId) {
        return participants.stream()
                .anyMatch(p -> p.getUserId().equals(userId) && p.isActive());
    }

    public void validAlreadyParticipatedUser(Long userId) {
        if (isAlreadyJoined(userId)) {
            throw new IllegalStateException("User has already joined this group buy");
        }
    }
}
