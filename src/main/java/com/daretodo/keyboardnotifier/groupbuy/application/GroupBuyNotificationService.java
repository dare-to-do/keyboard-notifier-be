package com.daretodo.keyboardnotifier.groupbuy.application;

import com.daretodo.keyboardnotifier.groupbuy.domain.*;
import com.daretodo.keyboardnotifier.user.domain.User;
import com.daretodo.keyboardnotifier.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupBuyNotificationService {
    private final SesClient sesClient;
    private final GroupBuyRepository groupBuyRepository;
    private final GroupBuyNotificationRepository groupBuyNotificationRepository;
    private final UserRepository userRepository;
    @Value("${app.email}")
    private String senderEmail;

    @Scheduled(cron = "${app.schedules.cron.group-buy-notification}")
    @Transactional
    public void scheduleDailyNotification() {
        LocalDate today = LocalDate.now();
        LocalDateTime todayNoon = today.atTime(12, 0);
        LocalDateTime tomorrowNoon = today.plusDays(1).atTime(12, 0);
        log.info("scheduleDailyNotification start: {}", todayNoon);

        List<GroupBuy> groupBuys = groupBuyRepository.findAllByStartDateTimeBetween(todayNoon, tomorrowNoon);

        sendGroupBuyStartNotification(groupBuys);
    }

    private void sendGroupBuyStartNotification(List<GroupBuy> groupBuys) {
        for (GroupBuy groupBuy : groupBuys) {
            List<GroupBuyParticipant> participants = groupBuy.getParticipants();
            log.info("participants:" + participants);

            for (GroupBuyParticipant participant : participants) {
                User user = userRepository.findById(participant.getUserId());
                SendEmailResponse sendEmailResponse = sendEmail(user.getEmail(), "제목", "내용");

                if (sendEmailResponse != null && sendEmailResponse.messageId() != null) {
                    groupBuyNotificationRepository.save(GroupBuyNotification.builder()
                        .receiverId(user.getId())
                        .groupBuyId(groupBuy.getId())
                        .status(GroupBuyNotificationStatus.SENT)
                        .build());
                    continue;
                }
                groupBuyNotificationRepository.save(GroupBuyNotification.builder()
                    .receiverId(user.getId())
                    .groupBuyId(groupBuy.getId())
                    .status(GroupBuyNotificationStatus.FAILED)
                    .build());
            }
        }
    }

    private SendEmailResponse sendEmail(String recipient, String subject, String bodyHTML) {
        Destination destination = Destination.builder()
                .toAddresses(recipient)
                .build();

        Content content = Content.builder()
                .data(bodyHTML)
                .build();

        Content sub = Content.builder()
                .data(subject)
                .build();

        Body body = Body.builder()
                .html(content)
                .build();

        Message msg = Message.builder()
                .subject(sub)
                .body(body)
                .build();

        SendEmailRequest emailRequest = SendEmailRequest.builder()
                .destination(destination)
                .message(msg)
                .source(senderEmail)
                .build();

        try {
            return sesClient.sendEmail(emailRequest);
        } catch (SesException e) {
            log.info(e.awsErrorDetails().errorMessage());
        }

        return null;
    }
}
