package com.daretodo.keyboardnotifier.groupbuy.domain;

public interface GroupBuyNotificationRepository {
    void updateStatus(GroupBuyNotification groupBuyNotification,GroupBuyNotificationStatus groupBuyNotificationStatus);

    void save(GroupBuyNotification groupBuyNotification);
}
