package com.daretodo.keyboardnotifier.groupbuy.domain;

public interface GroupBuyParticipantRepository {
    void updateStatus(Long participantId, GroupBuyParticipantStatus groupBuyParticipantStatus);
}
