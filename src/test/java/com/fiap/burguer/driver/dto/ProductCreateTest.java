package com.fiap.burguer.driver.dto;
import com.fiap.burguer.core.application.enums.CategoryProduct;
import com.fiap.burguer.core.application.enums.CategoryProduct;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductCreateTest {

    @Test
    public void testConstructorAndGetters() {
        String name = "Cheeseburger";
        String image = "cheeseburger.png";
        Integer preparationTime = 10;
        String description = "Delicious cheeseburger with fresh ingredients";
        double price = 9.99;
        CategoryProduct category = CategoryProduct.DRINK;
        ProductCreate productCreate = new ProductCreate(name, image, preparationTime, description, price, category);
        assertEquals(name, productCreate.getName());
        assertEquals(image, productCreate.getImage());
        assertEquals(preparationTime, productCreate.getPreparationTime());
        assertEquals(description, productCreate.getDescription());
        assertEquals(price, productCreate.getPrice());
        assertEquals(category, productCreate.getCategory());
    }

    @Test
    public void testSetters() {
        ProductCreate productCreate = new ProductCreate(null, null, null, null, 0.0, null);

        String name = "Veggie Burger";
        String image = "veggie_burger.png";
        Integer preparationTime = 15;
        String description = "Healthy and tasty veggie burger";
        double price = 8.99;
        CategoryProduct category = CategoryProduct.SNACK;
        productCreate.setName(name);
        productCreate.setImage(image);
        productCreate.setPreparationTime(preparationTime);
        productCreate.setDescription(description);
        productCreate.setPrice(price);
        productCreate.setCategory(category);
        assertEquals(name, productCreate.getName());
        assertEquals(image, productCreate.getImage());
        assertEquals(preparationTime, productCreate.getPreparationTime());
        assertEquals(description, productCreate.getDescription());
        assertEquals(price, productCreate.getPrice());
        assertEquals(category, productCreate.getCategory());
    }
}
