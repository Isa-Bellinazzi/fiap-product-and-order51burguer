package com.fiap.burguer.infraestructure.mappers;

import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.domain.Order;
import com.fiap.burguer.core.domain.OrderItem;
import com.fiap.burguer.infraestructure.entities.OrderEntity;
import com.fiap.burguer.infraestructure.entities.OrderItemEntity;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {

    @Test
    void testToEntityWithValidOrder() {
        Order order = new Order();
        order.setId(100);
        order.setTimeWaitingOrder(30);
        order.setDateCreated(new Date());
        order.setStatus(StatusOrder.WAITINGPAYMENT);
        order.setTotalPrice(199.99);

        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem item = new OrderItem();
        item.setId(1L);
        item.setPreparationTime("10");
        item.setDescription("51 burger");
        item.setTotalProductPrice(19.99);
        item.setAmount(2);
        orderItems.add(item);
        order.setOrderItemsList(orderItems);

        OrderEntity entity = OrderMapper.toEntity(order);

        assertNotNull(entity);
        assertEquals(order.getId(), entity.getId());
        assertEquals(order.getTimeWaitingOrder(), entity.getTimeWaitingOrder());
        assertEquals(order.getDateCreated(), entity.getDateCreated());
        assertEquals(order.getStatus(), entity.getStatus());
        assertEquals(order.getTotalPrice(), entity.getTotalPrice());
        assertNotNull(entity.getOrderItemsList());
        assertEquals(1, entity.getOrderItemsList().size());

        OrderItemEntity itemEntity = entity.getOrderItemsList().getFirst();
        assertEquals(item.getId(), itemEntity.getId());
        assertEquals(item.getPreparationTime(), itemEntity.getPreparationTime());
        assertEquals(item.getDescription(), itemEntity.getDescription());
        assertEquals(item.getTotalProductPrice(), itemEntity.getTotalProductPrice());
        assertEquals(item.getAmount(), itemEntity.getAmount());
    }

    @Test
    void testToDomainWithValidOrderEntity() {
        OrderEntity entity = new OrderEntity();
        entity.setId(100);
        entity.setTimeWaitingOrder(30);
        entity.setDateCreated(new Date());
        entity.setStatus(StatusOrder.WAITINGPAYMENT);
        entity.setTotalPrice(199.99);
        entity.setIdClient(1);

        List<OrderItemEntity> orderItems = new ArrayList<>();
        OrderItemEntity itemEntity = new OrderItemEntity();
        itemEntity.setId(1L);
        itemEntity.setPreparationTime("10");
        itemEntity.setDescription("51 burger");
        itemEntity.setTotalProductPrice(19.99);
        itemEntity.setAmount(2);
        orderItems.add(itemEntity);
        entity.setOrderItemsList(orderItems);

        Order domain = OrderMapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getTimeWaitingOrder(), domain.getTimeWaitingOrder());
        assertEquals(entity.getDateCreated(), domain.getDateCreated());
        assertEquals(entity.getStatus(), domain.getStatus());
        assertEquals(entity.getTotalPrice(), domain.getTotalPrice());
        assertNotNull(domain.getOrderItemsList());
        assertEquals(1, domain.getOrderItemsList().size());

        OrderItem item = domain.getOrderItemsList().getFirst();
        assertEquals(itemEntity.getId(), item.getId());
        assertEquals(itemEntity.getPreparationTime(), item.getPreparationTime());
        assertEquals(itemEntity.getDescription(), item.getDescription());
        assertEquals(itemEntity.getTotalProductPrice(), item.getTotalProductPrice());
        assertEquals(itemEntity.getAmount(), item.getAmount());
    }

    @Test
    void testToDomainListWithValidOrderEntities() {
        List<OrderEntity> entities = new ArrayList<>();
        OrderEntity entity = new OrderEntity();
        entity.setId(100);
        entity.setTimeWaitingOrder(30);
        entity.setDateCreated(new Date());
        entity.setStatus(StatusOrder.WAITINGPAYMENT);
        entity.setTotalPrice(199.99);
        entity.setIdClient(1);

        List<OrderItemEntity> orderItems = new ArrayList<>();
        OrderItemEntity itemEntity = new OrderItemEntity();
        itemEntity.setId(1L);
        itemEntity.setPreparationTime("10");
        itemEntity.setDescription("51 burger");
        itemEntity.setTotalProductPrice(19.99);
        itemEntity.setAmount(2);
        orderItems.add(itemEntity);
        entity.setOrderItemsList(orderItems);

        entities.add(entity);

        List<Order> domains = OrderMapper.toDomain(entities);

        assertNotNull(domains);
        assertEquals(1, domains.size());
        assertEquals(entity.getId(), domains.getFirst().getId());
    }


    @Test
    void testToEntityWithNullOrder() {
        OrderEntity entity = OrderMapper.toEntity(null);
        assertNull(entity, "O objeto convertido deveria ser null.");
    }

    @Test void testToDomainWithNullOrderEntity() {
        Order domain = OrderMapper.toDomain((OrderEntity) null);
        assertNull(domain, "O objeto convertido deveria ser null.");
}

    @Test
    void testToDomainListWithNullOrderEntities() {
        List<Order> domains = OrderMapper.toDomain((List<OrderEntity>) null);
        assertNull(domains, "A lista convertida deveria ser null.");
    }
}
