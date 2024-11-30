package com.fiap.burguer.core.application.usecases;

import com.fiap.burguer.core.application.exception.RequestException;
import com.fiap.burguer.core.application.exception.ResourceNotFoundException;
import com.fiap.burguer.core.application.ports.AuthenticationPort;
import com.fiap.burguer.driver.dto.ProductCreate;
import com.fiap.burguer.core.application.enums.CategoryProduct;
import com.fiap.burguer.core.application.ports.ProductPort;
import com.fiap.burguer.core.domain.Product;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

public class ProductUseCases {
    private final ProductPort productPort;
    private final AuthenticationPort authenticationPort;

    public ProductUseCases(ProductPort productPort, AuthenticationPort authenticationPort) {
        this.productPort = productPort;
        this.authenticationPort = authenticationPort;

    }

    public Product saveProduct(ProductCreate productCreate, String authorizationHeader) {
        authenticationPort.validateAuthorizationHeader(authorizationHeader);
        authenticationPort.validateIsAdminAccess(authorizationHeader);

        Product product = new Product();
        product.setName(productCreate.getName());
        product.setCategory(productCreate.getCategory());
        product.setPrice(productCreate.getPrice());
        product.setDescription(productCreate.getDescription());
        product.setPreparationTime(productCreate.getPreparationTime());
        product.setImage(productCreate.getImage());

        return productPort.save(product);
    }

    public Product updateProduct(Product productEntity, String authorizationHeader) {
        authenticationPort.validateAuthorizationHeader(authorizationHeader);
        authenticationPort.validateIsAdminAccess(authorizationHeader);

        Integer productId = productEntity.getId();

        Product existingProduct = productPort.findById(productId);
        if (existingProduct == null) {
            throw new EntityNotFoundException("Product with ID " + productId + " not found");
        }

        if (productEntity.getName() != null) {
            existingProduct.setName(productEntity.getName());
            existingProduct.setCategory(productEntity.getCategory());
            existingProduct.setPrice(productEntity.getPrice());
            existingProduct.setDescription(productEntity.getDescription());
            existingProduct.setPreparationTime(productEntity.getPreparationTime());
            existingProduct.setImage(productEntity.getImage());
        }

        return productPort.save(existingProduct);
    }

    public Product findById(int id, String authorizationHeader) {
        authenticationPort.validateAuthorizationHeader(authorizationHeader);
        if(id < 1 ) throw new RequestException("Id do Produto inválido");
        Product product = productPort.findById(id);
        if(product == null) throw new ResourceNotFoundException("Produto não encontrado");
        return product;

    }

    public List<Product> findByCategory(CategoryProduct category, String authorizationHeader) {
        authenticationPort.validateAuthorizationHeader(authorizationHeader);

        List<Product> allProductEntities = productPort.findAll();

        List<Product> filteredProductEntities = allProductEntities.stream()
                .filter(product -> product.getCategory() ==  category)
                .toList();

        if (filteredProductEntities == null || filteredProductEntities.isEmpty()) {
           throw new ResourceNotFoundException("Nenhum produto encontrado para a categoria " + category);
        }
        return filteredProductEntities;
    }

    public void deleteById(int id, String authorizationHeader) {
        authenticationPort.validateAuthorizationHeader(authorizationHeader);
        authenticationPort.validateIsAdminAccess(authorizationHeader);
        this.verifyProductExistance(id,authorizationHeader);
        productPort.deleteById(id);
    }

    public boolean verifyProductExistance(int id, String authorizationHeader){
        this.findById(id, authorizationHeader);
        return true;
    }
}
