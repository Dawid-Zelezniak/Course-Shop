package com.zelezniak.project.common;

import com.zelezniak.project.exception.ErrorInfo;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

import static com.zelezniak.project.common.AttributesAndTemplatesNames.ERROR_INFO_ATTRIBUTE;

public final class FormErrorHandler {

    public static ModelAndView getErrors(BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            List<ErrorInfo> errorInfos =
                    bindingResult.getAllErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .map(ErrorInfo::new)
                            .collect(Collectors.toList());
            return modelAndView.addObject(ERROR_INFO_ATTRIBUTE, errorInfos);
        } else {return null;}
    }
}