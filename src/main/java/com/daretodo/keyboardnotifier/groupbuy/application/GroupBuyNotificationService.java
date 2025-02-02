package com.daretodo.keyboardnotifier.groupbuy.application;

import com.daretodo.keyboardnotifier.groupbuy.domain.*;
import com.daretodo.keyboardnotifier.product.domain.Product;
import com.daretodo.keyboardnotifier.product.domain.ProductRepository;
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

import static com.daretodo.keyboardnotifier.common.util.DateUtil.formatDateWithTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupBuyNotificationService {
    private final SesClient sesClient;
    private final GroupBuyRepository groupBuyRepository;
    private final GroupBuyNotificationRepository groupBuyNotificationRepository;
    private final GroupBuyParticipantRepository groupBuyParticipantRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Value("${app.email}")
    private String senderEmail;

    @Value("${app.feedback-form-link}")
    private String feedbackFormLink;

    @Scheduled(cron = "${app.schedules.cron.group-buy-notification}")
    @Transactional
    public void scheduleDailyNotification() {
        LocalDate today = LocalDate.now();
        LocalDateTime todayNoon = today.atTime(12, 0);
        LocalDateTime tomorrowNoon = today.plusDays(1).atTime(12, 0);

        List<GroupBuy> groupBuys = groupBuyRepository.findAllByStartDateTimeBetween(todayNoon, tomorrowNoon);

        sendGroupBuyStartNotification(groupBuys);
    }

    private void sendGroupBuyStartNotification(List<GroupBuy> groupBuys) {
        for (GroupBuy groupBuy : groupBuys) {
            List<GroupBuyParticipant> participants = groupBuy.getParticipants();

            for (GroupBuyParticipant participant : participants) {
                if (participant.getStatus() != GroupBuyParticipantStatus.PARTICIPATED) {
                    continue;
                }

                User user = userRepository.findById(participant.getUserId());
                Product product = productRepository.findById(groupBuy.getProductId());

                String title = generateStartTitle(product.getName());
                String content = generateStartContent(
                        product.getName(),
                        formatDateWithTime(groupBuy.getEndDateTime()),
                        product.getProductUrl(),
                        feedbackFormLink
                );

                SendEmailResponse sendEmailResponse = sendEmail(user.getEmail(), title, content);

                if (sendEmailResponse != null && sendEmailResponse.messageId() != null) {
                    groupBuyNotificationRepository.save(GroupBuyNotification.builder()
                        .messageId(sendEmailResponse.messageId())
                        .receiverId(user.getId())
                        .groupBuyId(groupBuy.getId())
                        .status(GroupBuyNotificationStatus.SENT)
                        .build());
                    groupBuyParticipantRepository.updateStatus(participant.getId(), GroupBuyParticipantStatus.COMPLETED);
                    continue;
                }
                groupBuyNotificationRepository.save(GroupBuyNotification.builder()
                    .messageId(null)
                    .receiverId(user.getId())
                    .groupBuyId(groupBuy.getId())
                    .status(GroupBuyNotificationStatus.FAILED)
                    .build());
            }
        }
    }

    private SendEmailResponse sendEmail(String recipient, String subject, String bodyContent) {
        Destination destination = Destination.builder()
                .toAddresses(recipient)
                .build();

        Content content = Content.builder()
                .data(bodyContent)
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

    private String generateStartTitle(String productName) {
        return String.format("[Sokey]%s 공제 시작", productName);
    }

    private String generateStartContent(String productName, String endDate, String productLink, String feedbackFormLink) {
        return String.format(
                "<html>" +
                        "<body style='font-family: Arial, sans-serif; line-height: 1.6; color: #333;'>" +
                        "<p>안녕하세요, <b>Sokey</b>에서 공제 일정 소식 알려드립니다.</p>" +
                        "<p>관심상품으로 등록하신 <b>%s</b> 공제가 오늘부터 <b>%s</b>까지 진행 될 예정입니다.</p>" +
                        "<p>관련 상세 정보는 아래 링크를 통해 확인하세요.</p>" +
                        "<p><a href='%s' style='color: #0066cc; text-decoration: none;'><b>%s</b></a></p>" +
                        "<p>Sokey 서비스를 이용하시면서 불편하셨던 점이나 개선 사항이 있다면 문의 남겨주세요.</p>" +
                        "<p><a href='%s' style='color: #0066cc; text-decoration: none;'><b>%s</b></a></p>" +
                        "</body>" +
                        "</html>",
                productName, endDate, productLink, productLink, feedbackFormLink, feedbackFormLink
        );
    }

}
