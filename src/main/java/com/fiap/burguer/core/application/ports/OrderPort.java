package com.fiap.burguer.core.application.ports;

import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.domain.Order;
import com.fiap.burguer.infraestructure.entities.OrderEntity;

import java.util.List;

public interface OrderPort {
    Order save(Order order, String authorizationHeader);
    Order findById(int id);
    List<Order> findAll();
    List<Order> findByStatus(StatusOrder status);
    void deleteById(int id);
    void createCheckout (OrderEntity orderEntity, String authorizationHeader);
}
