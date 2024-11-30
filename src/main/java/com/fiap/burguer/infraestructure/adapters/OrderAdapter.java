package com.fiap.burguer.infraestructure.adapters;

import com.fiap.burguer.infraestructure.entities.OrderEntity;
import com.fiap.burguer.infraestructure.repository.OrderRepository;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.ports.OrderPort;
import com.fiap.burguer.core.domain.Order;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class OrderAdapter implements OrderPort {
    @Autowired
    OrderRepository orderRepository;

    private ModelMapper modelMapper;

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = modelMapper.map(order, OrderEntity.class);
        OrderEntity orderEntityResponse = orderRepository.save(orderEntity);

        return modelMapper.map(orderEntityResponse, Order.class);
    }

    @Override
    public Order findById(int id) {
        OrderEntity orderEntityResponse = orderRepository.findById(id);

        return modelMapper.map(orderEntityResponse, Order.class);
    }

    @Override
    public List<Order> findAll() {
        List<OrderEntity> orderEntityResponse = orderRepository.findAllOrders();

        return orderEntityResponse.stream()
                .map(orderEntity -> modelMapper.map(orderEntity, Order.class))
                .toList();

    }

    @Override
    public List<Order> findByStatus(StatusOrder status) {
        List<OrderEntity> orderEntityResponse = orderRepository.findByStatus(status);

        return orderEntityResponse.stream()
                .map(orderEntity -> modelMapper.map(orderEntity, Order.class))
                .toList();

    }

    @Override
    public void deleteById(int id) {
        orderRepository.deleteById(id);
    }
}





