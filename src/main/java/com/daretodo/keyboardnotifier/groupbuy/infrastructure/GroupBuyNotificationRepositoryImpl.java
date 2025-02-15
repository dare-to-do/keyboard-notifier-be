package com.daretodo.keyboardnotifier.groupbuy.infrastructure;

import com.daretodo.keyboardnotifier.groupbuy.domain.GroupBuyNotification;
import com.daretodo.keyboardnotifier.groupbuy.domain.GroupBuyNotificationRepository;
import com.daretodo.keyboardnotifier.groupbuy.domain.GroupBuyNotificationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class GroupBuyNotificationRepositoryImpl implements GroupBuyNotificationRepository {
    private final GroupBuyNotificationJpaRepository groupBuyNotificationJpaRepository;

    @Override
    public void updateStatus(GroupBuyNotification groupBuyNotification, GroupBuyNotificationStatus groupBuyNotificationStatus) {
        GroupBuyNotificationEntity groupBuyNotificationEntity = findByGroupBuyId(groupBuyNotification.getId());
        groupBuyNotificationEntity.updateStatus(groupBuyNotificationStatus);
    }

    @Override
    public void save(GroupBuyNotification groupBuyNotification) {
        groupBuyNotificationJpaRepository.save(GroupBuyNotificationEntity.from(groupBuyNotification));
    }

    private GroupBuyNotificationEntity findByGroupBuyId(Long groupBuyId) {
        return groupBuyNotificationJpaRepository.findById(groupBuyId).orElseThrow(
                () -> new IllegalArgumentException("공제알림을 찾을 수 없습니다.")
        );
    }
}
