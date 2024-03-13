package com.zelezniak.project.access;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static com.zelezniak.project.controller.AttributesAndTemplatesNames.ACCESS_DENIED_VIEW;
import static com.zelezniak.project.controller.AttributesAndTemplatesNames.LOGIN_VIEW;

@Controller
public class LoginController {

    @GetMapping({"/login/page"})
    public String loginPage() {
        return LOGIN_VIEW;
    }

    @GetMapping({"/access-denied"})
    public String showAccessDenied() {
        return ACCESS_DENIED_VIEW;
    }
}