package com.zelezniak.project.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Set;

import static com.zelezniak.project.common.AttributesAndTemplatesNames.*;

@Service
@RequiredArgsConstructor
public class OrderViewService implements OrderView {

    private final OrderService orderService;

    public ModelAndView getUserOrders(Principal principal) {
        ModelAndView modelAndView = new ModelAndView(USER_ORDERS_VIEW);
        Set<Order> ordersForUser = orderService.getOrdersForUser(principal);
        BigDecimal totalPrice = OrderService.totalOrdersPrice(ordersForUser);
        modelAndView.addObject(TOTAL_PRICE_ATTRIBUTE, totalPrice);
        modelAndView.addObject(ORDERS_ATTRIBUTE, ordersForUser);
        return modelAndView;
    }
}
