package com.zelezniak.project.controller;

import com.zelezniak.project.exception.CourseException;
import com.zelezniak.project.exception.ErrorInfo;
import com.zelezniak.project.service.UserService;
import com.zelezniak.project.user.UserData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.zelezniak.project.controller.AttributesAndTemplatesNames.*;

@Controller
@Slf4j
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping({"/registration"})
    public String showRegistrationForm(Model model) {
        model.addAttribute(WEB_USER_ATTRIBUTE, new UserData());
        return REGISTRATION_VIEW;
    }

    @PostMapping({"/registration/process"})
    public ModelAndView processRegistrationForm(@Valid @ModelAttribute(WEB_USER_ATTRIBUTE) UserData user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView(REGISTRATION_VIEW);
        ModelAndView errors = FormValidationManager.getErrors(bindingResult, modelAndView);
        if (errors != null) {return errors;} else {
            try {
                this.userService.createNewUser(user);
                modelAndView.addObject(INFO_ATTRIBUTE, "User created successfully");
                return modelAndView;
            } catch (CourseException var6) {
                return modelAndView.addObject(
                        ERROR_INFO_ATTRIBUTE, new ErrorInfo(var6.getCourseError().getMessage()));
            }
        }
    }
}