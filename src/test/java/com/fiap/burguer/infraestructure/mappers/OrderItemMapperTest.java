package com.fiap.burguer.infraestructure.mappers;

import com.fiap.burguer.core.domain.Product;
import com.fiap.burguer.infraestructure.entities.OrderEntity;
import com.fiap.burguer.infraestructure.entities.OrderItemEntity;
import com.fiap.burguer.core.domain.Order;
import com.fiap.burguer.core.domain.OrderItem;
import com.fiap.burguer.infraestructure.entities.ProductEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemMapperTest {

    @Test
    void testToEntity() {
        Product product = new Product();
        product.setName("Burger");
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setProduct(product);
        orderItem.setAmount(2);
        orderItem.setTotalProductPrice(10.99);
        OrderItemEntity orderItemEntity = OrderItemMapper.toEntity(orderItem);
        assertNotNull(orderItemEntity);
        assertEquals(orderItem.getId(), orderItemEntity.getId());
        assertEquals(orderItem.getProduct().getName(), orderItemEntity.getProduct().getName());
        assertEquals(orderItem.getAmount(), orderItemEntity.getAmount());
        assertEquals(orderItem.getTotalProductPrice(), orderItemEntity.getTotalProductPrice());
    }

    @Test
    void testToDomain() {
        ProductEntity product = new ProductEntity();
        product.setName("Burger");
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setId(1L);
        orderItemEntity.setProduct(product);
        orderItemEntity.setAmount(2);
        orderItemEntity.setTotalProductPrice(10.99);
        OrderItem orderItem = OrderItemMapper.toDomain(orderItemEntity);
        assertNotNull(orderItem);
        assertEquals(orderItemEntity.getId(), orderItem.getId());
        assertEquals(orderItemEntity.getProduct().getName(), orderItem.getProduct().getName());
        assertEquals(orderItemEntity.getAmount(), orderItem.getAmount());
        assertEquals(orderItemEntity.getTotalProductPrice(), orderItem.getTotalProductPrice());
    }

    @Test
    void testToDomainList() {
        ProductEntity product = new ProductEntity();
        ProductEntity product2 = new ProductEntity();
        product.setName("Burger");
        product2.setName("Fries");
        OrderItemEntity orderItemEntity1 = new OrderItemEntity();
        orderItemEntity1.setId(1L);
        orderItemEntity1.setProduct(product);
        orderItemEntity1.setAmount(2);
        orderItemEntity1.setTotalProductPrice(10.99);
        OrderItemEntity orderItemEntity2 = new OrderItemEntity();
        orderItemEntity2.setId(2L);
        orderItemEntity2.setProduct(product2);
        orderItemEntity2.setAmount(1);
        orderItemEntity2.setTotalProductPrice(5.49);
        List<OrderItemEntity> orderItemEntities = List.of(orderItemEntity1, orderItemEntity2);
        List<OrderItem> orderItems = OrderItemMapper.toDomain(orderItemEntities);
        assertNotNull(orderItems);
        assertEquals(2, orderItems.size());
        assertEquals(orderItemEntity1.getId(), orderItems.get(0).getId());
        assertEquals(orderItemEntity2.getProduct().getName(), orderItems.get(1).getProduct().getName());
    }

    @Test
    void testToDomainList_NullInput() {
        List<OrderItem> orderItems = OrderItemMapper.toDomain((List<OrderItemEntity>) null);
        assertNull(orderItems);
    }

    @Test
    void testToEntityList() {
        Product product = new Product();
        Product product2 = new Product();
        product.setName("Burger");
        product2.setName("Fries");
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setId(1L);
        orderItem1.setProduct(product);
        orderItem1.setAmount(2);
        orderItem1.setTotalProductPrice(10.99);
        OrderItem orderItem2 = new OrderItem();
        orderItem2.setId(2L);
        orderItem2.setProduct(product2);
        orderItem2.setAmount(1);
        orderItem2.setTotalProductPrice(5.49);
        List<OrderItem> orderItems = List.of(orderItem1, orderItem2);
        List<OrderItemEntity> orderItemEntities = OrderItemMapper.toEntity(orderItems);
        assertNotNull(orderItemEntities);
        assertEquals(2, orderItemEntities.size());
        assertEquals(orderItem1.getId(), orderItemEntities.get(0).getId());
        assertEquals(orderItem2.getProduct().getName(), orderItemEntities.get(1).getProduct().getName());
    }

    @Test
    void testToEntityList_NullInput() {
        List<OrderItemEntity> orderItemEntities = OrderItemMapper.toEntity((List<OrderItem>) null);
        assertNull(orderItemEntities);
    }

    @Test
    void testToEntityOrderItemOrder() {
        Product product = new Product();
        product.setName("Burger");
        Order order = new Order();
        order.setId(1);
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setProduct(product);
        orderItem.setAmount(2);
        orderItem.setTotalProductPrice(10.99);
        OrderItemEntity orderItemEntity = OrderItemMapper.toEntity(order, orderItem);
        assertNotNull(orderItemEntity);
        assertNotNull(orderItemEntity.getOrder());
        assertEquals(order.getId(), orderItemEntity.getOrder().getId());
        assertEquals(orderItem.getProduct(), orderItemEntity.getProduct());
    }

    @Test
    void testToEntityOrderItemOrder_NullInput() {
        OrderItemEntity orderItemEntity = OrderItemMapper.toEntity(null, null);
        assertNull(orderItemEntity);
    }

    @Test
    void testToDomainOrderItemOrder() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1);
        ProductEntity product = new ProductEntity();
        product.setName("Burger");
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setId(1L);
        orderItemEntity.setProduct(product);
        orderItemEntity.setAmount(2);
        orderItemEntity.setTotalProductPrice(10.99);
        OrderItem orderItem = OrderItemMapper.toDomain(orderEntity, orderItemEntity);
        assertNotNull(orderItem);
        assertNotNull(orderItem.getOrder());
        assertEquals(orderEntity.getId(), orderItem.getOrder().getId());
        assertEquals(orderItemEntity.getProduct().getName(), orderItem.getProduct().getName());
    }

    @Test
    void testToDomainOrderItemOrder_NullInput() {
        OrderItem orderItem = OrderItemMapper.toDomain(null, null);
        assertNull(orderItem);
    }

    @Test
    void testToDomainOrderItemsFromOrder() {
        ProductEntity product = new ProductEntity();
        product.setName("Burger");
        ProductEntity product2 = new ProductEntity();
        product.setName("Fries");
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(1);
        OrderItemEntity orderItemEntity1 = new OrderItemEntity();
        orderItemEntity1.setId(1L);
        orderItemEntity1.setProduct(product);
        orderItemEntity1.setAmount(2);
        orderItemEntity1.setTotalProductPrice(10.99);
        OrderItemEntity orderItemEntity2 = new OrderItemEntity();
        orderItemEntity2.setId(2L);
        orderItemEntity2.setProduct(product2);
        orderItemEntity2.setAmount(1);
        orderItemEntity2.setTotalProductPrice(5.49);
        orderEntity.setOrderItemsList(List.of(orderItemEntity1, orderItemEntity2));
        List<OrderItem> orderItems = OrderItemMapper.toDomain(orderEntity);
        assertNotNull(orderItems);
        assertEquals(2, orderItems.size());
        assertEquals(orderItemEntity1.getId(), orderItems.get(0).getId());
        assertEquals(orderItemEntity2.getProduct().getName(), orderItems.get(1).getProduct().getName());
    }

    @Test
    void testToDomainOrderItemsFromOrder_NullInput() {
        List<OrderItem> orderItems = OrderItemMapper.toDomain((OrderEntity) null);
        assertNull(orderItems);
    }

    @Test
    void testToEntityOrderItemsFromOrder() {
        Product product = new Product();
        product.setName("Burger");
        Product product2 = new Product();
        product.setName("Fries");
        Order order = new Order();
        order.setId(1);
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setId(1L);
        orderItem1.setProduct(product);
        orderItem1.setAmount(2);
        orderItem1.setTotalProductPrice(10.99);
        OrderItem orderItem2 = new OrderItem();
        orderItem2.setId(2L);
        orderItem2.setProduct(product2);
        orderItem2.setAmount(1);
        orderItem2.setTotalProductPrice(5.49);
        order.setOrderItemsList(List.of(orderItem1, orderItem2));
        List<OrderItemEntity> orderItemEntities = OrderItemMapper.toEntity(order);
        assertNotNull(orderItemEntities);
        assertEquals(2, orderItemEntities.size());
        assertEquals(orderItem1.getId(), orderItemEntities.get(0).getId());
        assertEquals(orderItem2.getProduct().getName(), orderItemEntities.get(1).getProduct().getName());
    }

    @Test
    void testToEntityOrderItemsFromOrder_NullInput() {
        List<OrderItemEntity> orderItemEntities = OrderItemMapper.toEntity((Order) null);
        assertNull(orderItemEntities);
    }
}
