package com.daretodo.keyboardnotifier.groupbuy.domain;

import java.util.ArrayList;
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
        this.participants = participants != null ? new ArrayList<>(participants) : new ArrayList<>();
    }

    public boolean canJoin() {
        return status == GroupBuyStatus.IN_PROGRESS &&
               LocalDateTime.now().isBefore(endDateTime);
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
