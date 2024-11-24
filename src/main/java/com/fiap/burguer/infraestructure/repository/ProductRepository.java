package com.fiap.burguer.infraestructure.repository;

import com.fiap.burguer.infraestructure.entities.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Integer> {
    ProductEntity save(ProductEntity productEntity);
    List<ProductEntity> findAll();
    ProductEntity findById(int id);
    void deleteById(int id);
}
