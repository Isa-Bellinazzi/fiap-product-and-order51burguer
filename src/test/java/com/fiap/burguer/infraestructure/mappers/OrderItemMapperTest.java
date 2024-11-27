package com.fiap.burguer.infraestructure.mappers;

import com.fiap.burguer.core.domain.Product;
import com.fiap.burguer.infraestructure.entities.OrderEntity;
import com.fiap.burguer.infraestructure.entities.OrderItemEntity;
import com.fiap.burguer.core.domain.Order;
import com.fiap.burguer.core.domain.OrderItem;
import com.fiap.burguer.infraestructure.entities.ProductEntity;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemMapperTest {

    @Test
    void testToEntityWithValidOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setAmount(2);
        orderItem.setDescription("Item de teste");
        orderItem.setPreparationTime("10");
        orderItem.setTotalProductPrice(50.0);

        OrderItemEntity entity = OrderItemMapper.toEntity(orderItem);

        assertNotNull(entity);
        assertEquals(orderItem.getId(), entity.getId());
        assertEquals(orderItem.getAmount(), entity.getAmount());
        assertEquals(orderItem.getDescription(), entity.getDescription());
        assertEquals(orderItem.getPreparationTime(), entity.getPreparationTime());
        assertEquals(orderItem.getTotalProductPrice(), entity.getTotalProductPrice());
    }

    @Test
    void testToDomainWithValidOrderItemEntity() {
        OrderItemEntity entity = new OrderItemEntity();
        entity.setId(1L);
        entity.setAmount(3);
        entity.setDescription("Entidade de teste");
        entity.setPreparationTime("15");
        entity.setTotalProductPrice(75.0);

        OrderItem domain = OrderItemMapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(entity.getId(), domain.getId());
        assertEquals(entity.getAmount(), domain.getAmount());
        assertEquals(entity.getDescription(), domain.getDescription());
        assertEquals(entity.getPreparationTime(), domain.getPreparationTime());
        assertEquals(entity.getTotalProductPrice(), domain.getTotalProductPrice());
    }

    @Test
    void testToEntityListWithValidOrderItems() {
        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem item1 = new OrderItem();
        item1.setId(1L);
        item1.setAmount(2);
        item1.setDescription("Item 1");
        item1.setPreparationTime("10");
        item1.setTotalProductPrice(30.0);

        OrderItem item2 = new OrderItem();
        item2.setId(2L);
        item2.setAmount(1);
        item2.setDescription("Item 2");
        item2.setPreparationTime("20");
        item2.setTotalProductPrice(50.0);

        orderItems.add(item1);
        orderItems.add(item2);

        List<OrderItemEntity> entities = OrderItemMapper.toEntity(orderItems);

        assertNotNull(entities);
        assertEquals(2, entities.size());
        assertEquals(orderItems.get(0).getId(), entities.get(0).getId());
        assertEquals(orderItems.get(1).getDescription(), entities.get(1).getDescription());
    }

    @Test
    void testToDomainListWithValidOrderItemEntities() {
        List<OrderItemEntity> entities = new ArrayList<>();
        OrderItemEntity entity1 = new OrderItemEntity();
        entity1.setId(1L);
        entity1.setAmount(3);
        entity1.setDescription("Entity 1");
        entity1.setPreparationTime("15");
        entity1.setTotalProductPrice(45.0);

        OrderItemEntity entity2 = new OrderItemEntity();
        entity2.setId(2L);
        entity2.setAmount(2);
        entity2.setDescription("Entity 2");
        entity2.setPreparationTime("25");
        entity2.setTotalProductPrice(60.0);

        entities.add(entity1);
        entities.add(entity2);

        List<OrderItem> domains = OrderItemMapper.toDomain(entities);

        assertNotNull(domains);
        assertEquals(2, domains.size());
        assertEquals(entities.get(0).getId(), domains.get(0).getId());
        assertEquals(entities.get(1).getDescription(), domains.get(1).getDescription());
    }

    @Test
    void testAllArgsConstructor() {
        OrderEntity orderEntity = new OrderEntity();
        ProductEntity productEntity = new ProductEntity();

        OrderItemEntity orderItemEntity = new OrderItemEntity(
                1L,
                orderEntity,
                productEntity,
                20.0,
                "10",
                "51 Burger",
                2
        );

        assertNotNull(orderItemEntity);
        assertEquals(1L, orderItemEntity.getId());
        assertEquals(orderEntity, orderItemEntity.getOrder());
        assertEquals(productEntity, orderItemEntity.getProduct());
        assertEquals(20.0, orderItemEntity.getTotalProductPrice());
        assertEquals("10", orderItemEntity.getPreparationTime());
        assertEquals("51 Burger", orderItemEntity.getDescription());
        assertEquals(2, orderItemEntity.getAmount());
    }

    @Test
    void testToEntityWithNullOrderItem() {
        OrderItemEntity entity = OrderItemMapper.toEntity((OrderItem) null);
        assertNull(entity, "O objeto convertido deveria ser null.");
    }
    
    @Test
    void testToDomainWithNullOrderItemEntity() {
        OrderItem domain = OrderItemMapper.toDomain((OrderItemEntity) null);
        assertNull(domain, "O objeto convertido deveria ser null.");
    }


    @Test
    void testToEntityListWithNullOrderItems() {
        List<OrderItemEntity> entities = OrderItemMapper.toEntity((List<OrderItem>) null);
        assertNull(entities, "A lista convertida deveria ser null.");
    }


    @Test
    void testToDomainListWithNullOrderItemEntities() {
        List<OrderItem> domains = OrderItemMapper.toDomain((List<OrderItemEntity>) null);
        assertNull(domains, "A lista convertida deveria ser null.");
    }

    @Test
    void testToDomainList_NullInput() {
        List<OrderItem> orderItems = OrderItemMapper.toDomain((List<OrderItemEntity>) null);
        assertNull(orderItems);
    }


    @Test
    void testToEntityList_NullInput() {
        List<OrderItemEntity> orderItemEntities = OrderItemMapper.toEntity((List<OrderItem>) null);
        assertNull(orderItemEntities);
    }

    @Test
    void testToEntityOrderItemOrder_NullInput() {
        OrderItemEntity orderItemEntity = OrderItemMapper.toEntity(null, null);
        assertNull(orderItemEntity);
    }

    @Test
    void testToDomainOrderItemOrder_NullInput() {
        OrderItem orderItem = OrderItemMapper.toDomain(null, null);
        assertNull(orderItem);
    }

    @Test
    void testToDomainOrderItemsFromOrder_NullInput() {
        List<OrderItem> orderItems = OrderItemMapper.toDomain((OrderEntity) null);
        assertNull(orderItems);
    }

    @Test
    void testToEntityOrderItemsFromOrder_NullInput() {
        List<OrderItemEntity> orderItemEntities = OrderItemMapper.toEntity((Order) null);
        assertNull(orderItemEntities);
    }

}
