package com.fiap.burguer.core.application.usecases;

import com.fiap.burguer.core.application.Exception.RequestException;
import com.fiap.burguer.core.application.Exception.ResourceNotFoundException;
import com.fiap.burguer.core.application.enums.CategoryProduct;
import com.fiap.burguer.core.application.ports.AuthenticationPort;
import com.fiap.burguer.core.application.ports.ProductPort;
import com.fiap.burguer.core.domain.Product;
import com.fiap.burguer.driver.dto.ProductCreate;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductUseCasesTest {

    @Mock
    private ProductPort productPort;

    @Mock
    private AuthenticationPort authenticationPort;

    private ProductUseCases productUseCases;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productUseCases = new ProductUseCases(productPort, authenticationPort);
    }

    @Test
    void testSaveProduct_Success() {
        ProductCreate productCreate = new ProductCreate("Burger","image.jpg" , 10, "Delicious burger", 15,CategoryProduct.DESSERT );
        String authorizationHeader = "Bearer token";

        Product savedProduct = new Product();
        savedProduct.setId(1);
        when(productPort.save(any(Product.class))).thenReturn(savedProduct);

        Product result = productUseCases.saveProduct(productCreate, authorizationHeader);

        verify(productPort).save(any(Product.class));

        assertEquals(1, result.getId());
    }

    @Test
    void testUpdateProduct_Success() {
        Product existingProduct = new Product();
        existingProduct.setName("Burger");
        existingProduct.setId(1);
        existingProduct.setCategory(CategoryProduct.DRINK);
        existingProduct.setPrice( 10.0);
        existingProduct.setDescription("Delicious burger");
        existingProduct.setPreparationTime(15);
        existingProduct.setImage("image.jpg");

        Product updatedProduct = new Product();
        updatedProduct.setName("Updated Burger");
        updatedProduct.setId(1);
        updatedProduct.setCategory(CategoryProduct.SNACK);
        updatedProduct.setPrice( 12.0);
        updatedProduct.setDescription("Even better burger");
        updatedProduct.setPreparationTime(20);
        updatedProduct.setImage("new_image.jpg");

        String authorizationHeader = "Bearer token";

        when(productPort.findById(existingProduct.getId())).thenReturn(existingProduct);
        when(productPort.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productUseCases.updateProduct(updatedProduct, authorizationHeader);

        verify(productPort).findById(existingProduct.getId());
        verify(productPort).save(existingProduct);

        assertEquals("Updated Burger", result.getName());
    }

    @Test
    void testUpdateProduct_NotFound() {
        Product product = new Product();
        product.setId(999);
        String authorizationHeader = "Bearer token";
        when(productPort.findById(product.getId())).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> productUseCases.updateProduct(product, authorizationHeader));
    }

    @Test
    void testFindById_Success() {
        Product product = new Product();
        product.setName("Burger");
        product.setId(1);
        product.setCategory(CategoryProduct.DRINK);
        product.setPrice( 10.0);
        product.setDescription("Delicious burger");
        product.setPreparationTime(15);
        product.setImage("image.jpg");
        String authorizationHeader = "Bearer token";

        when(productPort.findById(1)).thenReturn(product);

        Product result = productUseCases.findById(1, authorizationHeader);
        verify(productPort).findById(1);

        assertEquals(1, result.getId());
    }

    @Test
    void testFindById_InvalidId() {
        String authorizationHeader = "Bearer token";

        assertThrows(RequestException.class, () -> productUseCases.findById(0, authorizationHeader));
    }

    @Test
    void testFindByCategory_Success() {
        Product product = new Product();
        product.setName("Burger");
        product.setCategory(CategoryProduct.SNACK);
        product.setPrice(10.0);
        product.setDescription("Delicious burger");
        product.setPreparationTime(15);
        product.setImage("image.jpg");

        List<Product> products = List.of(product);
        String authorizationHeader = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjcGYiOiI3NzU4MjkzMDAwMiIsIm5hbWUiOiJNYXJpYSBOdW5lcyIsImlkIjoyLCJpc0FkbWluIjp0cnVlLCJleHAiOjE3MzQxOTM1MTgsImVtYWlsIjoibWFyaWFOdW5lc0BleGFtcGxlLmNvbSJ9.2mOK0LBKuy2lAXFrEuoUQxTvHzXq8ypDS8vnW-b3sD8";

        when(productPort.findAll()).thenReturn(products);

        List<Product> result = productUseCases.findByCategory(CategoryProduct.SNACK, authorizationHeader);

        verify(authenticationPort).validateAuthorizationHeader(authorizationHeader);
        verify(productPort).findAll();

        assertEquals(1, result.size());
        assertEquals("Burger", result.get(0).getName());
    }

    @Test
    void testFindByCategory_NotFound() {
        String authorizationHeader = "Bearer token";
        when(productPort.findAll()).thenReturn(Collections.emptyList());
        assertThrows(ResourceNotFoundException.class, () -> productUseCases.findByCategory(CategoryProduct.SNACK, authorizationHeader));
    }

    @Test
    void testDeleteById_Success() {
        int productId = 1;
        String authorizationHeader ="Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjcGYiOiI3NzU4MjkzMDAwMiIsIm5hbWUiOiJNYXJpYSBOdW5lcyIsImlkIjoyLCJpc0FkbWluIjp0cnVlLCJleHAiOjE3MzQxOTM1MTgsImVtYWlsIjoibWFyaWFOdW5lc0BleGFtcGxlLmNvbSJ9.2mOK0LBKuy2lAXFrEuoUQxTvHzXq8ypDS8vnW-b3sD8";
        Product product = new Product();
        product.setId(productId);
        when(productPort.findById(productId)).thenReturn(product);
        productUseCases.deleteById(productId, authorizationHeader);
        verify(productPort).deleteById(productId);
    }

    @Test
    void testVerifyProductExistance_Success() {
        int productId = 1;
        String authorizationHeader = "Bearer token";
        Product product = new Product();
        product.setId(productId);
        when(productPort.findById(productId)).thenReturn(product);
        boolean result = productUseCases.verifyProductExistance(productId, authorizationHeader);
        verify(productPort).findById(productId);
        assertTrue(result);
    }
}
