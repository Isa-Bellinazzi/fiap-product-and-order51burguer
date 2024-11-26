package com.fiap.burguer.core.application.ports;
import com.fiap.burguer.core.domain.Product;

import java.util.List;

public interface ProductPort {
    Product save(Product product);
    Product findById(int id);
    List<Product> findAll();
    void deleteById(int id);
}
