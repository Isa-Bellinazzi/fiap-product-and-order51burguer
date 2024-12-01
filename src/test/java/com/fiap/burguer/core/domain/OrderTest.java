package com.fiap.burguer.core.domain;

import com.fiap.burguer.core.application.enums.StatusOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

 class OrderTest {
    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(1);
        order.setTimeWaitingOrder(30);
        order.setDateCreated(new Date());
        order.setStatus(StatusOrder.PREPARATION);
        order.setTotalPrice(50.75);
        order.setOrderItemsList(new ArrayList<>());
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1, order.getId());
        assertEquals(30, order.getTimeWaitingOrder());
        assertNotNull(order.getDateCreated());
        assertEquals(StatusOrder.PREPARATION, order.getStatus());
        assertEquals(50.75, order.getTotalPrice());
        assertTrue(order.getOrderItemsList().isEmpty());
    }

    @Test
    void testSetOrderItemsList() {
        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem());
        order.setOrderItemsList(items);
        assertEquals(1, order.getOrderItemsList().size());
    }

    @Test
    void testSetStatus() {
        order.setStatus(StatusOrder.READY);
        assertEquals(StatusOrder.READY, order.getStatus());
    }

    @Test
    void testTotalPriceUpdate() {
        order.setTotalPrice(100.0);
        assertEquals(100.0, order.getTotalPrice());
    }
}
