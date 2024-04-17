package com.zelezniak.project.order;

import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

public interface OrderView {
    ModelAndView getUserOrders(Principal principal);
}
