package com.zelezniak.emailservice.mail;

import com.zelezniak.emailservice.dto.EmailInfo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Async
    public void prepareEmail(EmailInfo emailInfo) {
        String title = "Your Course Purchase Confirmation:" + emailInfo.getTitle();
        String content = buildEmailContent(emailInfo);
        try {
            buildAndSendEmail(emailInfo.getEmail(), title, content);
        } catch (MessagingException e) {
            log.info("Something went wrong while sending the email");
        }
    }

    private void buildAndSendEmail(String to, String title, String content) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(title);
        mimeMessageHelper.setText(content);
        mailSender.send(mimeMessage);
    }

    private String buildEmailContent(EmailInfo emailInfo) {
        return "Dear " + emailInfo.getFirstName() + " " + emailInfo.getLastName() + "\n" +
                "Thank you for purchasing the course " + emailInfo.getTitle() + "\n" +
                "COURSE DETAILS:" + "\n" +
                "Course Title:" + emailInfo.getTitle() +"\n" +
                "Price:" + emailInfo.getPrice() +"\n" +
                "Transaction ID:" + emailInfo.getOrderId();
    }
}
