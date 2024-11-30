package com.fiap.burguer.driver.controller;
import com.fiap.burguer.api.ProductApi;
import com.fiap.burguer.driver.dto.ProductCreate;
import com.fiap.burguer.core.application.enums.CategoryProduct;
import com.fiap.burguer.core.application.usecases.ProductUseCases;
import com.fiap.burguer.core.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController implements ProductApi {
    ProductUseCases productUseCases;
    public ProductController(ProductUseCases productUseCases) {
        this.productUseCases = productUseCases;
    }


    public ResponseEntity<Product> getProductById(int id, String authorizationHeader) {
        Product product = productUseCases.findById(id, authorizationHeader);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    public ResponseEntity<List<Product>> getProductsByCategory(CategoryProduct category, String authorizationHeader) {
            List<Product> productEntities = productUseCases.findByCategory(category, authorizationHeader);
            return ResponseEntity.ok(productEntities);
    }

    public Product postProduct(ProductCreate productCreate, String authorizationHeader) {
        return productUseCases.saveProduct(productCreate, authorizationHeader);
    }


    public Product putProduct(Product product, String authorizationHeader) {
        return productUseCases.updateProduct(product, authorizationHeader);
    }


    public ResponseEntity deleteProduct(int id, String authorizationHeader) {
            productUseCases.deleteById(id, authorizationHeader);
            return new ResponseEntity<>("Produto Deletado com sucesso",HttpStatus.OK);

    }
}

