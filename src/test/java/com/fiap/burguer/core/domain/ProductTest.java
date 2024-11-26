package com.fiap.burguer.core.domain;
import com.fiap.burguer.core.application.enums.CategoryProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1);
        product.setName("Burger");
        product.setCategory(CategoryProduct.SNACK);
        product.setPrice(15.99);
        product.setDescription("A delicious beef burger");
        product.setPreparationTime(10);
        product.setImage("burger.png");
    }

    @Test
    void testGettersAndSetters() {
        assertEquals(1, product.getId());
        assertEquals("Burger", product.getName());
        assertEquals(CategoryProduct.SNACK, product.getCategory());
        assertEquals(15.99, product.getPrice());
        assertEquals("A delicious beef burger", product.getDescription());
        assertEquals(10, product.getPreparationTime());
        assertEquals("burger.png", product.getImage());
    }

    @Test
    void testSetName() {
        product.setName("Cheeseburger");
        assertEquals("Cheeseburger", product.getName());
    }

    @Test
    void testSetCategory() {
        product.setCategory(CategoryProduct.DRINK);
        assertEquals(CategoryProduct.DRINK, product.getCategory());
    }

    @Test
    void testSetPrice() {
        product.setPrice(20.50);
        assertEquals(20.50, product.getPrice());
    }

    @Test
    void testSetDescription() {
        product.setDescription("Updated description");
        assertEquals("Updated description", product.getDescription());
    }

    @Test
    void testSetPreparationTime() {
        product.setPreparationTime(15);
        assertEquals(15, product.getPreparationTime());
    }

    @Test
    void testSetImage() {
        product.setImage("new_image.png");
        assertEquals("new_image.png", product.getImage());
    }
}


