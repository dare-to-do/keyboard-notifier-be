package com.daretodo.keyboardnotifier.email.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;
import software.amazon.awssdk.services.ses.model.SendEmailResponse;
import software.amazon.awssdk.services.ses.model.SesException;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailSendService {
    private final SesClient sesClient;

    public SendEmailResponse sendEmail(SendEmailRequest emailRequest) {
        try {
            return sesClient.sendEmail(emailRequest);
        } catch (SesException e) {
            log.info(e.awsErrorDetails().errorMessage());
        }

        return null;
    }
}
