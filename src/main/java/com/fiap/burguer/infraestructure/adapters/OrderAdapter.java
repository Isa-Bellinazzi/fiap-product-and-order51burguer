package com.fiap.burguer.infraestructure.adapters;

import com.fiap.burguer.infraestructure.entities.OrderEntity;
import com.fiap.burguer.infraestructure.repository.OrderRepository;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.ports.OrderPort;
import com.fiap.burguer.core.domain.Order;
import org.modelmapper.ModelMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderAdapter implements OrderPort {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    public OrderAdapter(OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Order save(Order order, String authorizationHeader) {
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
