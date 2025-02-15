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

        LocalDate tomorrow = today.plusDays(1);
        LocalDateTime tomorrowMidnight = tomorrow.atTime(0,0);
        LocalDateTime theDayAfterTomorrowMidnight = tomorrow.plusDays(1).atTime(0, 0);

        List<GroupBuy> startGroupBuys = groupBuyRepository
                .findAllByStartDateTimeBetween(todayNoon, tomorrowNoon);

        List<GroupBuy> endGroupBuys = groupBuyRepository
                .findAllByEndDateTimeBetween(tomorrowMidnight, theDayAfterTomorrowMidnight);

        groupBuyNotificationService.sendGroupBuyStartNotification(startGroupBuys);
        groupBuyNotificationService.sendGroupBuyEndNotification(endGroupBuys);
    }

}
