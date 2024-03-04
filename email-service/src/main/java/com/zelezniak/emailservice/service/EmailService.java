package com.zelezniak.emailservice.service;

import com.zelezniak.emailservice.dto.EmailInfo;

public interface EmailService {

    void prepareEmail(EmailInfo emailInfo);
}
