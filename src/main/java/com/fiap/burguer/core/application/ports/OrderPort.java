package com.fiap.burguer.core.application.ports;

import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.domain.Order;

import java.util.List;

public interface OrderPort {
    Order save(Order order);
    Order findById(int id);
    List<Order> findAll();
    List<Order> findByStatus(StatusOrder status);
    void deleteById(int id);
}
