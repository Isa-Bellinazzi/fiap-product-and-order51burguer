package com.fiap.burguer.infraestructure.entities;

import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.domain.OrderItem;
import com.fiap.burguer.infraestructure.mappers.OrderItemMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EntitiesTest {

    @Test
    void testNoArgsConstructor() {
        OrderItemEntity entity = new OrderItemEntity();
        assertNull(entity.getId());
        assertNull(entity.getOrder());
        assertNull(entity.getProduct());
        assertNull(entity.getTotalProductPrice());
        assertNull(entity.getPreparationTime());
        assertNull(entity.getDescription());
        assertEquals(1, entity.getAmount());
    }

    @Test
    void testSettersAndGetters() {
        OrderItemEntity entity = new OrderItemEntity();
        OrderEntity order = new OrderEntity();
        ProductEntity product = new ProductEntity();
        entity.setOrder(order);
        entity.setProduct(product);
        entity.setTotalProductPrice(10.50);
        entity.setPreparationTime("15 minutes");
        entity.setDescription("Cheese burger");
        entity.setAmount(3);
        assertEquals(order, entity.getOrder());
        assertEquals(product, entity.getProduct());
        assertEquals(10.50, entity.getTotalProductPrice());
        assertEquals("15 minutes", entity.getPreparationTime());
        assertEquals("Cheese burger", entity.getDescription());
        assertEquals(3, entity.getAmount());
    }

    @Test
    void testAllArgsConstructor() {
        Date dateCreated = new Date();
        StatusOrder statusOrder = StatusOrder.WAITINGPAYMENT;
        OrderItemEntity orderItemEntity = new OrderItemEntity();

        OrderEntity orderEntity = new OrderEntity(
                1,
                30,
                dateCreated,
                statusOrder,
                150.0,
                List.of(orderItemEntity),
                123
        );

        assertNotNull(orderEntity);
        assertEquals(1, orderEntity.getId());
        assertEquals(30, orderEntity.getTimeWaitingOrder());
        assertEquals(dateCreated, orderEntity.getDateCreated());
        assertEquals(statusOrder, orderEntity.getStatus());
        assertEquals(150.0, orderEntity.getTotalPrice());
        assertEquals(1, orderEntity.getOrderItemsList().size());
        assertEquals(123, orderEntity.getIdClient());
    }
    @Test
    void testToStringMethod() {
        OrderEntity order = new OrderEntity();
        ProductEntity product = new ProductEntity();
        Double price = 10.50;
        String preparationTime = "15 minutes";
        String description = "Cheese burger";
        Integer amount = 3;
        OrderItemEntity entity = new OrderItemEntity();
        entity.setOrder(order);
        entity.setProduct(product);
        entity.setTotalProductPrice(price);
        entity.setPreparationTime(preparationTime);
        entity.setDescription(description);
        entity.setAmount(amount);
        assertTrue(entity.toString().contains("OrderItemEntity"));
        assertTrue(entity.toString().contains("totalProductPrice=10.5"));
        assertTrue(entity.toString().contains("amount=3"));
    }

    @Test
    void testEqualsAndHashCode() {
        OrderEntity order = new OrderEntity();
        ProductEntity product = new ProductEntity();
        Double price = 10.50;
        String preparationTime = "15 minutes";
        String description = "Cheese burger";
        Integer amount = 3;
        OrderItemEntity entity1 = new OrderItemEntity();
        entity1.setOrder(order);
        entity1.setProduct(product);
        entity1.setTotalProductPrice(price);
        entity1.setPreparationTime(preparationTime);
        entity1.setDescription(description);
        entity1.setAmount(amount);
        OrderItemEntity entity2 = new OrderItemEntity();
        entity2.setOrder(order);
        entity2.setProduct(product);
        entity2.setTotalProductPrice(price);
        entity2.setPreparationTime(preparationTime);
        entity2.setDescription(description);
        entity2.setAmount(amount);
        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    void testNotEquals() {
        OrderEntity order1 = new OrderEntity();
        OrderEntity order2 = new OrderEntity();
        ProductEntity product1 = new ProductEntity();
        ProductEntity product2 = new ProductEntity();
        OrderItemEntity entity1 = new OrderItemEntity();
        entity1.setOrder(order1);
        entity1.setProduct(product1);
        entity1.setTotalProductPrice(10.50);
        entity1.setPreparationTime("15 minutes");
        entity1.setDescription("Cheese burger");
        entity1.setAmount(3);
        OrderItemEntity entity2 = new OrderItemEntity();
        entity2.setOrder(order2);
        entity2.setProduct(product2);
        entity2.setTotalProductPrice(12.00);
        entity2.setPreparationTime("10 minutes");
        entity2.setDescription("Veggie burger");
        entity2.setAmount(2);
        assertNotEquals(entity1, entity2);
        assertNotEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    void testToDomain() {
        OrderEntity order = new OrderEntity();
        order.setId(1);
        order.setTotalPrice(100.0);

        ProductEntity product = new ProductEntity();
        product.setId(10);
        product.setName("Burger");

        Double price = 10.50;
        String preparationTime = "15 minutes";
        String description = "Cheese burger";
        Integer amount = 3;

        OrderItemEntity orderItemEntity = new OrderItemEntity();
        orderItemEntity.setId(2L);
        orderItemEntity.setOrder(order);
        orderItemEntity.setDescription(description);
        orderItemEntity.setProduct(product);
        orderItemEntity.setAmount(amount);
        orderItemEntity.setPreparationTime(preparationTime);
        orderItemEntity.setTotalProductPrice(price);

        OrderItem orderItemDomain = OrderItemMapper.toDomain(order, orderItemEntity);

        assertEquals(order.getId(), orderItemDomain.getOrder().getId());
        assertEquals(price, orderItemDomain.getTotalProductPrice());
        assertEquals(preparationTime, orderItemDomain.getPreparationTime());
        assertEquals(description, orderItemDomain.getDescription());
        assertEquals(amount, orderItemDomain.getAmount());
    }
}
