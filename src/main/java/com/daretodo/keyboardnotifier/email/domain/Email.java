package com.daretodo.keyboardnotifier.email.domain;


import lombok.Getter;
import software.amazon.awssdk.services.ses.model.*;

@Getter
public class Email {

    private final String senderEmail;

    private final Destination destination;

    private final Content title;

    private final Content content;

    private Email(String senderEmail, String recipient, String title, String content) {
        this.senderEmail = senderEmail;
        this.destination = Destination.builder()
                .toAddresses(recipient)
                .build();
        this.title = Content.builder()
                .data(title)
                .build();
        this.content = Content.builder()
                .data(content)
                .build();
    }

    public static Email of(String senderEmail, String recipient, String title, String content) {
        return new Email(senderEmail, recipient, title, content);
    }

    public SendEmailRequest buildEmailResult() {
        return SendEmailRequest.builder()
                .destination(destination)
                .message(buildMessage())
                .source(senderEmail)
                .build();
    }

    private Message buildMessage() {
        return Message.builder()
                .subject(title)
                .body(buildBody())
                .build();
    }

    private Body buildBody() {
        return Body.builder()
                .html(content)
                .build();
    }
}
