//package com.fiap.burguer.infraestructure.mappers;
//
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class ProductMapperTest {
//
//    @Test
//    void testToEntity() {
//        Product product = new Product();
//        product.setId(1);
//        product.setName("Burger");
//        product.setPrice(10.99);
//
//        ProductEntity productEntity = ProductMapper.toEntity(product);
//
//        assertNotNull(productEntity);
//        assertEquals(product.getId(), productEntity.getId());
//        assertEquals(product.getName(), productEntity.getName());
//        assertEquals(product.getPrice(), productEntity.getPrice());
//    }
//
//
//
//    @Test
//    void testToDomain() {
//        ProductEntity productEntity = new ProductEntity();
//        productEntity.setId(1);
//        productEntity.setName("Burger");
//        productEntity.setPrice(10.99);
//        Product product = ProductMapper.toDomain(productEntity);
//        assertNotNull(product);
//        assertEquals(productEntity.getId(), product.getId());
//        assertEquals(productEntity.getName(), product.getName());
//        assertEquals(productEntity.getPrice(), product.getPrice());
//    }
//
//
//
//    @Test
//    void testToDomainList() {
//        ProductEntity productEntity1 = new ProductEntity();
//        productEntity1.setId(1);
//        productEntity1.setName("Burger");
//        productEntity1.setPrice(10.99);
//
//        ProductEntity productEntity2 = new ProductEntity();
//        productEntity2.setId(2);
//        productEntity2.setName("Fries");
//        productEntity2.setPrice(4.99);
//
//        List<ProductEntity> productEntities = List.of(productEntity1, productEntity2);
//        List<Product> products = ProductMapper.toDomain(productEntities);
//
//        assertNotNull(products);
//        assertEquals(2, products.size());
//        assertEquals(productEntity1.getId(), products.get(0).getId());
//        assertEquals(productEntity2.getName(), products.get(1).getName());
//    }
//
//
//}
