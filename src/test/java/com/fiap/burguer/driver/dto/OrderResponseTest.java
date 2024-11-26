package com.fiap.burguer.driver.dto;
import com.fiap.burguer.core.application.enums.CategoryProduct;
import com.fiap.burguer.core.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderResponseTest {
    private Product product1;
    private Product product2;

    @BeforeEach
    public void setup() {
        product1 = new Product();
        product1.setId(1);
        product1.setName("Burger");
        product1.setPrice(45.99);
        product1.setPreparationTime(2);
        product1.setCategory(CategoryProduct.SNACK);

        product2 = new Product();
        product2.setId(2);
        product2.setName("Burger");
        product2.setPrice(25.75);
        product2.setPreparationTime(15);
        product2.setCategory(CategoryProduct.SNACK);
    }
    @Test
    public void testConstructorAndGetters() {
        int id = 1;
        String status = "READY";
        double totalPrice = 45.99;
        Date dateCreated = new Date();
        double timeWaitingOrder = 30.5;
        List<Product> products = List.of(product1);

        OrderResponse orderResponse = new OrderResponse(id, status, totalPrice, dateCreated, timeWaitingOrder, products);

        assertEquals(id, orderResponse.getId());
        assertEquals(status, orderResponse.getStatus());
        assertEquals(totalPrice, orderResponse.getTotalPrice());
        assertEquals(dateCreated.getTime(), orderResponse.getDateCreated().getTime());
        assertEquals(timeWaitingOrder, orderResponse.getTimeWaitingOrder());
        assertEquals(products, orderResponse.getProducts());
    }

    @Test
    public void testDefaultConstructorAndSetters() {
        OrderResponse orderResponse = new OrderResponse();

        int id = 2;
        String status = "PENDING";
        double totalPrice = 25.75;
        Date dateCreated = new Date();
        double timeWaitingOrder = 15.0;
        List<Product> products = List.of(product2);

        orderResponse.setId(id);
        orderResponse.setStatus(status);
        orderResponse.setTotalPrice(totalPrice);
        orderResponse.setDateCreated(dateCreated);
        orderResponse.setTimeWaitingOrder(timeWaitingOrder);
        orderResponse.setProducts(products);

        assertEquals(id, orderResponse.getId());
        assertEquals(status, orderResponse.getStatus());
        assertEquals(totalPrice, orderResponse.getTotalPrice());
        assertEquals(dateCreated.getTime(), orderResponse.getDateCreated().getTime()); // Comparação por timestamp
        assertEquals(timeWaitingOrder, orderResponse.getTimeWaitingOrder());
        assertEquals(products, orderResponse.getProducts());
    }
}
