package com.fiap.burguer.driver.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.burguer.IntegrationTest;
import com.fiap.burguer.core.application.enums.CategoryProduct;
import com.fiap.burguer.core.application.usecases.ProductUseCases;
import com.fiap.burguer.core.domain.Product;
import com.fiap.burguer.driver.dto.ProductCreate;
import com.fiap.burguer.driver.handlers.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

 class ProductControllerTest extends IntegrationTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductUseCases productUseCases;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productUseCases = Mockito.mock(ProductUseCases.class);
        productController = new ProductController(productUseCases);

        mvc = MockMvcBuilders.standaloneSetup(productController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @Test
    void testGetProductByIdTest() throws Exception {
        when(this.productUseCases.findById(1, "authorizationHeader")).thenReturn(null);
        mvc.perform(get("/products/1")
                        .header("Authorization", "authorizationHeader"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetProductByCategoryTest() throws Exception {
        mvc.perform(get("/products/category/"+ CategoryProduct.DESSERT).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testPostProductTest() throws Exception {

        ProductCreate productCreate = new ProductCreate("name", "image", 40, "description", 10, CategoryProduct.DESSERT);
        when(this.productUseCases.saveProduct(productCreate, "authorizationHeader")).thenReturn(null);
        String json = new ObjectMapper().writeValueAsString(productCreate);

        mvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON).content(json)
                        .header("Authorization", "authorizationHeader"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateProductTest() throws Exception {
        Product product = new Product();
        when(this.productUseCases.updateProduct( product, "authorizationHeader")).thenReturn(null);
        String json = new ObjectMapper().writeValueAsString(product);

        mvc.perform(put("/products")
                        .contentType(MediaType.APPLICATION_JSON).content(json)
                        .header("Authorization", "authorizationHeader"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteProductTest() throws Exception {
        doNothing().when(this.productUseCases).deleteById(1, "authorizationHeader");
        mvc.perform(delete("/products/1")
                        .header("Authorization", "authorizationHeader"))
                .andExpect(status().isOk());
    }

}