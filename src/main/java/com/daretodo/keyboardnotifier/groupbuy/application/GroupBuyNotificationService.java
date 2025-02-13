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
    private final GroupBuyEmailContentGenerator groupBuyEmailContentGenerator;
    private final GroupBuyNotificationRepository groupBuyNotificationRepository;
    private final GroupBuyParticipantRepository groupBuyParticipantRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Value("${app.email}")
    private String senderEmail;

    @Value("${app.feedback-form-link}")
    private String feedbackFormLink;

    public void sendGroupBuyStartNotification(List<GroupBuy> groupBuys) {
        for (GroupBuy groupBuy : groupBuys) {
            List<GroupBuyParticipant> participants = groupBuy.getParticipants();

            for (GroupBuyParticipant participant : participants) {
                if (participant.getStatus() != GroupBuyParticipantStatus.PARTICIPATED) {
                    continue;
                }

                User user = userRepository.findById(participant.getUserId());
                Product product = productRepository.findById(groupBuy.getProductId());

                String title = groupBuyEmailContentGenerator.generateStartTitle(product.getName());
                String content = groupBuyEmailContentGenerator.generateStartContent(
                        product.getName(),
                        formatDateWithTime(groupBuy.getEndDateTime()),
                        product.getProductUrl(),
                        feedbackFormLink
                );

                Email email = Email.of(senderEmail, user.getEmail(), title, content);

                SendEmailRequest sendEmailRequest = email.buildEmailResult();
                SendEmailResponse sendEmailResponse = emailSendService.sendEmail(sendEmailRequest);

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
}
