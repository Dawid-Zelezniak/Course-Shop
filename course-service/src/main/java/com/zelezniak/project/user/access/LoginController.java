package com.zelezniak.project.user.access;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.zelezniak.project.common.AttributesAndTemplatesNames.ACCESS_DENIED_VIEW;
import static com.zelezniak.project.common.AttributesAndTemplatesNames.LOGIN_VIEW;

@Controller
final class LoginController {

    @GetMapping({"/login/page"})
    public String loginPage() {
        return LOGIN_VIEW;
    }

    @GetMapping({"/access-denied"})
    public String showAccessDenied() {
        return ACCESS_DENIED_VIEW;
    }
}