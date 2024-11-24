package com.fiap.burguer.driver.presenters;

import com.fiap.burguer.core.domain.Order;
import com.fiap.burguer.core.domain.OrderItem;
import com.fiap.burguer.core.domain.Product;
import com.fiap.burguer.driver.dto.OrderResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class OrderPresenterTest {

    private Order order;
    private Product product;

    @BeforeEach
    public void setup() {
        product = new Product();
        product.setId(1);
        product.setName("Burger");
        product.setPrice(10.0);

        order = new Order();
        order.setId(1);
        order.setStatus(null);
        order.setTotalPrice(10.0);
        order.setTimeWaitingOrder(5);
        order.setDateCreated(new Date());

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setPreparationTime("15");
        orderItem.setOrder(order);

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        order.setOrderItemsList(orderItems);
    }


    @Test
    public void testMapOrderToResponseWithValidOrder() {
        OrderResponse response = OrderPresenter.mapOrderToResponse(order);

        assertNotNull(response);
        assertEquals(order.getId(), response.getId());
        assertNull(response.getStatus());
        assertEquals(order.getTotalPrice(), response.getTotalPrice());
        assertEquals(order.getDateCreated(), response.getDateCreated());
        assertNotNull(response.getProducts());
        assertEquals(1, response.getProducts().size());
        assertEquals(product.getId(), response.getProducts().get(0).getId());
        assertEquals(product.getName(), response.getProducts().get(0).getName());
    }

    @Test
    public void testMapOrderToResponseWithNullOrder() {
        OrderPresenter.mapOrderToResponse(null);
        OrderResponse response = null;
        assertNull(response);
    }

    @Test
    public void testMapOrderToResponseWithNullOrderItems() {
        order.setOrderItemsList(null);

        OrderResponse response = OrderPresenter.mapOrderToResponse(order);

        assertNotNull(response);
        assertEquals(order.getId(), response.getId());
        assertNull(response.getStatus());
        assertEquals(order.getTotalPrice(), response.getTotalPrice());

        assertNotNull(response.getProducts());
        assertTrue(response.getProducts().isEmpty());
    }


    @Test
    public void testMapOrderToResponseWithEmptyOrderItems() {
        order.setOrderItemsList(new ArrayList<>());

        OrderResponse response = OrderPresenter.mapOrderToResponse(order);

        assertNotNull(response);
        assertEquals(order.getId(), response.getId());
        assertNull(response.getStatus());
        assertEquals(order.getTotalPrice(), response.getTotalPrice());
        assertNotNull(response.getProducts());
        assertTrue(response.getProducts().isEmpty());
    }
}
