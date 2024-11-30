package com.fiap.burguer.core.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {

    private OrderItem orderItem;

    @BeforeEach
    void setUp() {
        orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setAmount(2);
        orderItem.setDescription("Delicious burger combo");
        orderItem.setPreparationTime("15 minutes");
        orderItem.setTotalProductPrice(25.50);

        // Mocking dependencies
        Product product = new Product();
        product.setName("Burger");
        orderItem.setProduct(product);

        Order order = new Order();
        order.setId(101);
        orderItem.setOrder(order);
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1L, orderItem.getId());
        assertEquals(2, orderItem.getAmount());
        assertEquals("Delicious burger combo", orderItem.getDescription());
        assertEquals("15 minutes", orderItem.getPreparationTime());
        assertEquals(25.50, orderItem.getTotalProductPrice());

        assertNotNull(orderItem.getProduct());
        assertEquals("Burger", orderItem.getProduct().getName());

        assertNotNull(orderItem.getOrder());
        assertEquals(101, orderItem.getOrder().getId());
    }

    @Test
    void testSetAmount() {
        orderItem.setAmount(5);
        assertEquals(5, orderItem.getAmount());
    }

    @Test
    void testSetDescription() {
        orderItem.setDescription("Updated description");
        assertEquals("Updated description", orderItem.getDescription());
    }

    @Test
    void testSetPreparationTime() {
        orderItem.setPreparationTime("20 minutes");
        assertEquals("20 minutes", orderItem.getPreparationTime());
    }

    @Test
    void testSetTotalProductPrice() {
        orderItem.setTotalProductPrice(30.75);
        assertEquals(30.75, orderItem.getTotalProductPrice());
    }

    @Test
    void testSetProduct() {
        Product newProduct = new Product();
        newProduct.setName("Fries");
        orderItem.setProduct(newProduct);

        assertNotNull(orderItem.getProduct());
        assertEquals("Fries", orderItem.getProduct().getName());
    }

    @Test
    void testSetOrder() {
        Order newOrder = new Order();
        newOrder.setId(202);
        orderItem.setOrder(newOrder);

        assertNotNull(orderItem.getOrder());
        assertEquals(202, orderItem.getOrder().getId());
    }
}

