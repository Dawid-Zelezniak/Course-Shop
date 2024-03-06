package com.zelezniak.emailservice.mail;

import com.zelezniak.emailservice.dto.EmailInfo;

public interface EmailService {

    void prepareEmail(EmailInfo emailInfo);
}
