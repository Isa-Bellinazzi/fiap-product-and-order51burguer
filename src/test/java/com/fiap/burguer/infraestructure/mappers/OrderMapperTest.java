package com.fiap.burguer.infraestructure.mappers;

import com.fiap.burguer.core.domain.Order;
import com.fiap.burguer.core.domain.OrderItem;
import com.fiap.burguer.core.domain.Product;
import com.fiap.burguer.infraestructure.entities.OrderEntity;
import com.fiap.burguer.infraestructure.entities.OrderItemEntity;
import com.fiap.burguer.infraestructure.entities.ProductEntity;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderMapperTest {

    @Mock
    private OrderItemMapper orderItemMapper;  // Mockando o OrderItemMapper

    @InjectMocks
    private OrderMapper orderMapper;

    @Test
    public void testToEntity() {
        Product product = new Product();
        product.setName("Fries");

        ProductEntity productEntity = new ProductEntity();
        productEntity.setName("Burger");

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setAmount(2);
        Order order = new Order();
        order.setId(1);
        order.setOrderItemsList(List.of(orderItem));
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setProduct(productEntity);
        orderItemEntity.setAmount(2);
        Mockito.when(orderItemMapper.toEntity(order)).thenReturn(List.of(orderItemEntity));
        OrderEntity orderEntity = orderMapper.toEntity(order);
        assertNotNull(orderEntity);
        assertEquals(order.getId(), orderEntity.getId());
        assertEquals(1, orderEntity.getOrderItemsList().size());
        assertEquals(orderItem.getProduct().getName(), orderEntity.getOrderItemsList().get(0).getProduct().getName());
    }

    @Test
    public void testToDomain() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName("Fries");
        Product product = new Product();
        product.setName("Burger");
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setProduct(productEntity);
        orderItemEntity.setAmount(2);
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1);
        orderEntity.setOrderItemsList(List.of(orderItemEntity));

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setAmount(2);
        Mockito.when(orderItemMapper.toDomain(orderEntity)).thenReturn(List.of(orderItem));
        Order order = orderMapper.toDomain(orderEntity);
        assertNotNull(order);
        assertEquals(orderEntity.getId(), order.getId());
        assertEquals(1, order.getOrderItemsList().size());
        assertEquals(orderItem.getProduct().getName(), order.getOrderItemsList().get(0).getProduct().getName());
    }
}
