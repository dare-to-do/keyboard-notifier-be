package com.daretodo.keyboardnotifier.groupbuy.infrastructure;

import com.daretodo.keyboardnotifier.groupbuy.domain.GroupBuyParticipantRepository;
import com.daretodo.keyboardnotifier.groupbuy.domain.GroupBuyParticipantStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GroupBuyParticipantRepositoryImpl implements GroupBuyParticipantRepository {
    private final GroupBuyParticipantJpaRepository groupBuyParticipantJpaRepository;

    @Override
    public void updateStatus(Long participantId, GroupBuyParticipantStatus groupBuyParticipantStatus) {
        GroupBuyParticipantEntity groupBuyParticipantEntity = groupBuyParticipantJpaRepository
                .findById(participantId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공제 참여자입니다."));

        groupBuyParticipantEntity.updateStatus(groupBuyParticipantStatus);
    }
}
