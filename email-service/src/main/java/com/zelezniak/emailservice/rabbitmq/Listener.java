package com.zelezniak.emailservice.rabbitmq;

import com.zelezniak.emailservice.dto.EmailInfo;
import com.zelezniak.emailservice.exception.CustomErrors;
import com.zelezniak.emailservice.exception.EmailException;
import com.zelezniak.emailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RequiredArgsConstructor
public class Listener {

    private final EmailService emailService;

    private static final String QUEUE_NAME = "emails-queue";

    @RabbitListener(queues = QUEUE_NAME)
    public void getEmailInfoFromQueue(EmailInfo emailInfo) {
        if (emailInfo != null) {emailService.prepareEmail(emailInfo);}
        throw new EmailException(CustomErrors.EMAIL_INFO_NOT_FOUND);
    }
}
