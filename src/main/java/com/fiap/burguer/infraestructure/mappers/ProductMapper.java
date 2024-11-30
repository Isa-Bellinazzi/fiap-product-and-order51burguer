package com.fiap.burguer.infraestructure.mappers;

import com.fiap.burguer.infraestructure.entities.ProductEntity;
import com.fiap.burguer.core.domain.Product;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ProductMapper {

    private  ProductMapper(){}

    public static ProductEntity toEntity(Product product) {
        if(product == null) return null;
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(product, productEntity);
        return productEntity;
    }

    public static Product toDomain(ProductEntity productEntity) {
        if(productEntity == null) return null;
        Product product = new Product();
        BeanUtils.copyProperties(productEntity, product);
        return product;
    }

    public static List<Product> toDomain(List<ProductEntity> productEntities) {
        if(productEntities == null) return List.of();
        return productEntities.stream()
                .map(ProductMapper::toDomain)
                .toList();

    }
}
