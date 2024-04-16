package com.zelezniak.project.user.access;

import com.zelezniak.project.common.FormErrorHandler;
import com.zelezniak.project.exception.CourseException;
import com.zelezniak.project.exception.ErrorInfo;
import com.zelezniak.project.exception.UserException;
import com.zelezniak.project.user.UserData;
import com.zelezniak.project.user.UserService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import static com.zelezniak.project.common.AttributesAndTemplatesNames.*;

@Controller
@RequiredArgsConstructor
public final class RegistrationController {

    private final UserService userService;

    @GetMapping({"/registration"})
    public String showRegistrationForm(Model model) {
        model.addAttribute(WEB_USER_ATTRIBUTE, new UserData());
        return REGISTRATION_VIEW;
    }

    @PostMapping({"/registration/process"})
    public ModelAndView processRegistrationForm(@Valid @ModelAttribute(WEB_USER_ATTRIBUTE) UserData user,
                                                BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView(REGISTRATION_VIEW);
        ModelAndView errors = FormErrorHandler.getErrors(bindingResult, modelAndView);
        if (errors != null) {return errors;}
        else {
            try {
                userService.createNewUser(user);
                modelAndView.addObject(INFO_ATTRIBUTE, "User created successfully");
                return modelAndView;
            } catch (CourseException ex) {
                return modelAndView.addObject(ERROR_INFO_ATTRIBUTE, new ErrorInfo(ex.getCourseError().getMessage()));
            }catch (UserException ex){
                return modelAndView.addObject(ERROR_INFO_ATTRIBUTE, new ErrorInfo(ex.getUserError().getMessage()));
            }
        }
    }
}