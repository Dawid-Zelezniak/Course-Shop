package com.zelezniak;

import com.zelezniak.project.order.Order;
import com.zelezniak.project.order.OrderService;
import com.zelezniak.project.valueobjects.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderServiceTest {

    @Test
    @DisplayName(value = "check if calculated total price is correct")
    void should_calculate_total_orders_cost(){
        Order order = new Order();
        Order order2 = new Order();
        Order order3 = new Order();
        Order order4 = new Order();
        order.setCoursePrice(new Money("49.99"));
        order2.setCoursePrice(new Money("79.99"));
        order3.setCoursePrice(new Money("99.99"));
        order4.setCoursePrice(new Money("33.45"));
        assertEquals(BigDecimal.valueOf(129.98),OrderService.totalOrdersPrice(new HashSet<>(Arrays.asList(order,order2))));
        assertEquals(BigDecimal.valueOf(179.98),OrderService.totalOrdersPrice(new HashSet<>(Arrays.asList(order2,order3))));
        assertEquals(BigDecimal.valueOf(133.44),OrderService.totalOrdersPrice(new HashSet<>(Arrays.asList(order3,order4))));
        assertEquals(BigDecimal.valueOf(83.44),OrderService.totalOrdersPrice(new HashSet<>(Arrays.asList(order,order4))));
        assertEquals(BigDecimal.valueOf(263.42),OrderService.totalOrdersPrice(
                new HashSet<>(Arrays.asList(order,order2,order3,order4))));
    }
}
