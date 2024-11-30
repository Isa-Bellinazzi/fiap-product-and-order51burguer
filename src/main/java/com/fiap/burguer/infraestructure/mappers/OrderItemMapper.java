package com.fiap.burguer.infraestructure.mappers;

import com.fiap.burguer.infraestructure.entities.OrderEntity;
import com.fiap.burguer.infraestructure.entities.OrderItemEntity;
import com.fiap.burguer.core.domain.Order;
import com.fiap.burguer.core.domain.OrderItem;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class OrderItemMapper {

    private  OrderItemMapper(){}

    public static OrderItemEntity toEntity(OrderItem orderItem) {
        if(orderItem == null) return null;
        OrderItemEntity orderItemEntity = new OrderItemEntity();

        BeanUtils.copyProperties(orderItem, orderItemEntity);

        return orderItemEntity;
    }

    public static OrderItem toDomain(OrderItemEntity orderItemEntity) {
        if(orderItemEntity == null) return null;
        OrderItem orderItem = new OrderItem();

        BeanUtils.copyProperties(orderItemEntity, orderItem);

        return orderItem;
    }

    public static List<OrderItem> toDomain(List<OrderItemEntity> orderItemEntities) {
        if(orderItemEntities == null) return List.of();
        return orderItemEntities.stream()
                .map(OrderItemMapper::toDomain)
                .toList();

    }

    public static List<OrderItemEntity> toEntity(List<OrderItem> orderItems) {
        if(orderItems == null) return List.of();
        return orderItems.stream()
                .map(OrderItemMapper::toEntity)
                .toList();

    }


    public static OrderItemEntity toEntity(Order order, OrderItem orderItem) {
        if(order == null) return null;
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(orderItem, orderItemEntity);
        BeanUtils.copyProperties(order, orderEntity);

        orderItemEntity.setOrder(orderEntity);
        return orderItemEntity;
    }

    public static OrderItem toDomain(OrderEntity orderEntity, OrderItemEntity orderItemEntity) {
        if(orderEntity == null) return null;
        OrderItem orderItem = new OrderItem();
        Order order = new Order();

        BeanUtils.copyProperties(orderItemEntity, orderItem);
        BeanUtils.copyProperties(orderEntity, order);

        orderItem.setOrder(order);
        return orderItem;
    }

    public static List<OrderItem> toDomain(OrderEntity orderEntity) {
        if(orderEntity == null) return List.of();
        return orderEntity.getOrderItemsList().stream()
                .map(orderItemEnity -> OrderItemMapper.toDomain(orderEntity, orderItemEnity))
                .toList();

    }

    public static List<OrderItemEntity> toEntity(Order order) {
        if(order == null) return List.of();
        return order.getOrderItemsList().stream()
                .map(orderItem -> OrderItemMapper.toEntity(order, orderItem))
                .toList();

    }

}
