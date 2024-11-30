package com.fiap.burguer.infraestructure.mappers;

import com.fiap.burguer.infraestructure.entities.OrderEntity;
import com.fiap.burguer.infraestructure.entities.OrderItemEntity;
import com.fiap.burguer.core.domain.Order;
import com.fiap.burguer.core.domain.OrderItem;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class OrderMapper {

    private  OrderMapper(){

    }

    public static OrderEntity toEntity(Order order) {
        if(order == null) return null;
        OrderEntity orderEntity = new OrderEntity();
        List<OrderItemEntity> orderItemList = OrderItemMapper.toEntity(order);

        BeanUtils.copyProperties(order, orderEntity);
        BeanUtils.copyProperties(order.getOrderItemsList(), orderItemList);
        orderEntity.setOrderItemsList(orderItemList);
        return orderEntity;
    }

    public static Order toDomain(OrderEntity orderEntity) {
        if(orderEntity == null) return null;
        Order order = new Order();
        List<OrderItem> orderItemList = OrderItemMapper.toDomain(orderEntity);

        BeanUtils.copyProperties(orderEntity, order);
        BeanUtils.copyProperties(orderEntity.getOrderItemsList(), orderItemList);
        order.setOrderItemsList(orderItemList);

        return order;
    }

    public static List<Order> toDomain(List<OrderEntity> orderEntities) {
        if(orderEntities == null) return List.of();
        return orderEntities.stream()
                .map(OrderMapper::toDomain)
                .toList();

    }
}
