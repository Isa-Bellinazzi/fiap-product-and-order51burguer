package com.fiap.burguer.infraestructure.adapters;

import com.fiap.burguer.infraestructure.entities.ProductEntity;
import com.fiap.burguer.infraestructure.mappers.ProductMapper;
import com.fiap.burguer.infraestructure.repository.ProductRepository;
import com.fiap.burguer.core.application.ports.ProductPort;
import com.fiap.burguer.core.domain.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductAdapter implements ProductPort {

    private final ProductRepository productRepository;

    public ProductAdapter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product save(Product product) {
        ProductEntity productEntity = ProductMapper.toEntity(product);
        ProductEntity productEntityResponse = productRepository.save(productEntity);

        return ProductMapper.toDomain(productEntityResponse);
    }

    @Override
    public Product findById(int id) {
        ProductEntity productEntityResponse = productRepository.findById(id);

        return ProductMapper.toDomain(productEntityResponse);
    }

    @Override
    public List<Product> findAll() {
        List<ProductEntity> productEntityResponse = productRepository.findAll();

        return ProductMapper.toDomain(productEntityResponse);
    }

    @Override
    public void deleteById(int id) {
        productRepository.deleteById(id);
    }
}
