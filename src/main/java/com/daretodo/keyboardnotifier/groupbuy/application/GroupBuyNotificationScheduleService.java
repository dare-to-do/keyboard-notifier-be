package com.daretodo.keyboardnotifier.groupbuy.application;

import com.daretodo.keyboardnotifier.groupbuy.domain.GroupBuy;
import com.daretodo.keyboardnotifier.groupbuy.domain.GroupBuyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupBuyNotificationScheduleService {
    private final GroupBuyNotificationService groupBuyNotificationService;
    private final GroupBuyRepository groupBuyRepository;

    @Scheduled(cron = "${app.schedules.cron.group-buy-notification}")
    @Transactional
    public void scheduleDailyNotification() {
        LocalDate today = LocalDate.now();
        LocalDateTime todayNoon = today.atTime(12, 0);
        LocalDateTime tomorrowNoon = today.plusDays(1).atTime(12, 0);

        List<GroupBuy> groupBuys = groupBuyRepository.findAllByStartDateTimeBetween(todayNoon, tomorrowNoon);

        groupBuyNotificationService.sendGroupBuyStartNotification(groupBuys);
    }

}
