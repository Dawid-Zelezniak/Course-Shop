package com.zelezniak.project.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderView orderView;

    @GetMapping({"/user/orders"})
    public ModelAndView userOrders(Principal principal) {
        return orderView.getUserOrders(principal);
    }
}
