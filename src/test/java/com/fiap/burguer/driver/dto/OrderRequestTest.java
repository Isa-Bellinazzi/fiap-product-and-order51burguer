package com.fiap.burguer.driver.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

 class OrderRequestTest {

    @Test
     void testConstructorAndGetters() {
        int productId = 1;
        int quantity = 2;
        List<OrderRequest.OrderItemRequest> items = List.of(new OrderRequest.OrderItemRequest(productId, quantity));
        int idClient = 100;
        OrderRequest orderRequest = new OrderRequest(idClient, items);
        assertEquals(idClient, orderRequest.getIdClient());
        assertNotNull(orderRequest.getItems());
        assertEquals(1, orderRequest.getItems().size());
        assertEquals(productId, orderRequest.getItems().getFirst().getProductId());
        assertEquals(quantity, orderRequest.getItems().getFirst().getQuantity());
    }

    @Test
     void testDefaultConstructorAndSetters() {
        OrderRequest orderRequest = new OrderRequest();
        int productId = 1;
        int quantity = 3;
        List<OrderRequest.OrderItemRequest> items = List.of(new OrderRequest.OrderItemRequest(productId, quantity));
        int idClient = 101;
        orderRequest.setIdClient(idClient);
        orderRequest.setItems(items);
        assertEquals(idClient, orderRequest.getIdClient());
        assertNotNull(orderRequest.getItems());
        assertEquals(1, orderRequest.getItems().size());
        assertEquals(productId, orderRequest.getItems().getFirst().getProductId());
        assertEquals(quantity, orderRequest.getItems().getFirst().getQuantity());
    }

    @Test
     void testOrderItemRequestConstructorAndGetters() {
        int productId = 2;
        int quantity = 4;
        OrderRequest.OrderItemRequest orderItemRequest = new OrderRequest.OrderItemRequest(productId, quantity);
        assertEquals(productId, orderItemRequest.getProductId());
        assertEquals(quantity, orderItemRequest.getQuantity());
    }

    @Test
     void testOrderItemRequestSetters() {
        OrderRequest.OrderItemRequest orderItemRequest = new OrderRequest.OrderItemRequest(0, 0);
        int productId = 5;
        int quantity = 6;
        orderItemRequest.setProductId(productId);
        orderItemRequest.setQuantity(quantity);
        assertEquals(productId, orderItemRequest.getProductId());
        assertEquals(quantity, orderItemRequest.getQuantity());
    }

    @Test
     void testOrderItemRequestDefaultConstructor() {
        OrderRequest.OrderItemRequest orderItemRequest = new OrderRequest.OrderItemRequest();
        assertEquals(0, orderItemRequest.getProductId());
        assertEquals(0, orderItemRequest.getQuantity());
    }
}
