package com.daretodo.keyboardnotifier.groupbuy.application;

import com.daretodo.keyboardnotifier.email.application.EmailSendService;
import com.daretodo.keyboardnotifier.email.domain.Email;
import com.daretodo.keyboardnotifier.groupbuy.domain.*;
import com.daretodo.keyboardnotifier.product.domain.Product;
import com.daretodo.keyboardnotifier.product.domain.ProductRepository;
import com.daretodo.keyboardnotifier.user.domain.User;
import com.daretodo.keyboardnotifier.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;
import software.amazon.awssdk.services.ses.model.SendEmailResponse;

import java.util.List;

import static com.daretodo.keyboardnotifier.common.util.DateUtil.formatDateWithTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupBuyNotificationService {
    private final EmailSendService emailSendService;
    private final GroupBuyNotificationRepository groupBuyNotificationRepository;
    private final GroupBuyParticipantRepository groupBuyParticipantRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Value("${app.email}")
    private String senderEmail;

    @Value("${app.feedback-form-link}")
    private String feedbackFormLink;

    public void sendGroupBuyStartNotification(List<GroupBuy> groupBuys) {
        for (GroupBuy startGroupBuy : groupBuys) {
            List<GroupBuyParticipant> participants = startGroupBuy.getParticipants();

            for (GroupBuyParticipant participant : participants) {
                if (participant.getStatus() != GroupBuyParticipantStatus.PARTICIPATED) {
                    continue;
                }

                User user = userRepository.findById(participant.getUserId());
                Product product = productRepository.findById(startGroupBuy.getProductId());

                String title = GroupBuyEmailContentGenerator.generateStartTitle(product.getName());
                String content = GroupBuyEmailContentGenerator.generateStartContent(
                        product.getName(),
                        formatDateWithTime(startGroupBuy.getEndDateTime()),
                        product.getProductUrl(),
                        feedbackFormLink
                );

                Email email = Email.of(senderEmail, user.getEmail(), title, content);
                sendEmail(startGroupBuy, participant, user.getId(), email);
            }
        }
    }

    public void sendGroupBuyEndNotification(List<GroupBuy> groupBuys) {
        for (GroupBuy endGroupBuy : groupBuys) {
            List<GroupBuyParticipant> participants = endGroupBuy.getParticipants();

            for (GroupBuyParticipant participant : participants) {
                if (participant.getStatus() != GroupBuyParticipantStatus.PARTICIPATED) {
                    continue;
                }

                User user = userRepository.findById(participant.getUserId());
                Product product = productRepository.findById(endGroupBuy.getProductId());

                String title = GroupBuyEmailContentGenerator.generateEndTitle(product.getName());
                String content = GroupBuyEmailContentGenerator.generateEndContent(
                        product.getName(),
                        product.getProductUrl(),
                        feedbackFormLink
                );

                Email email = Email.of(senderEmail, user.getEmail(), title, content);
                sendEmail(endGroupBuy, participant, user.getId(), email);
            }
        }
    }

    private void sendEmail(GroupBuy groupBuy, GroupBuyParticipant participant, Long userId, Email email) {
        SendEmailRequest sendEmailRequest = email.buildEmailResult();
        SendEmailResponse sendEmailResponse = emailSendService.sendEmail(sendEmailRequest);

        if (sendEmailResponse != null && sendEmailResponse.messageId() != null) {
            groupBuyNotificationRepository.save(GroupBuyNotification.builder()
                    .messageId(sendEmailResponse.messageId())
                    .receiverId(userId)
                    .groupBuyId(groupBuy.getId())
                    .status(GroupBuyNotificationStatus.SENT)
                    .build());
            groupBuyParticipantRepository.updateStatus(participant.getId(), GroupBuyParticipantStatus.COMPLETED);
            return;
        }
        groupBuyNotificationRepository.save(GroupBuyNotification.builder()
                .messageId(null)
                .receiverId(userId)
                .groupBuyId(groupBuy.getId())
                .status(GroupBuyNotificationStatus.FAILED)
                .build());
    }
}
