package com.fiap.burguer.core.application.usecases;

import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.ports.AuthenticationPort;
import com.fiap.burguer.core.application.ports.OrderPort;
import com.fiap.burguer.core.application.ports.ProductPort;
import com.fiap.burguer.core.domain.Order;
import com.fiap.burguer.core.domain.OrderItem;
import com.fiap.burguer.core.domain.Product;
import com.fiap.burguer.driver.dto.OrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateOrderUseCaseTest {

    private OrderPort orderPort;
    private ValidateOrderUseCase validateOrderUseCase;
    private ProductPort productPort;
    private TimeWaitingOrderQueueUseCase timeWaitingOrderQueueUseCase;
    private AuthenticationPort authenticationPort;
    private CreateOrderUseCase createOrderUseCase;

    @BeforeEach
    void setUp() {
        orderPort = mock(OrderPort.class);
        validateOrderUseCase = mock(ValidateOrderUseCase.class);
        productPort = mock(ProductPort.class);
        timeWaitingOrderQueueUseCase = mock(TimeWaitingOrderQueueUseCase.class);
        authenticationPort = mock(AuthenticationPort.class);

        createOrderUseCase = new CreateOrderUseCase(orderPort, validateOrderUseCase, productPort, timeWaitingOrderQueueUseCase, authenticationPort);
    }

//    @Test
//    void shouldCreateOrderSuccessfully() {
//        // Arrange
//        String authorizationHeader = "Bearer token";
//        int clientId = 1;
//        Product product = new Product();
//        product.setId(1);
//        product.setPrice(10.0);
//        product.setPreparationTime(5);
//        product.setDescription("Burger");
//
//        OrderRequest.OrderItemRequest item = new OrderRequest.OrderItemRequest();
//        item.setProductId(1);
//        item.setQuantity(2);
//
//        OrderRequest orderRequest = new OrderRequest();
//        orderRequest.setItems(List.of(item));
//
//
//        when(authenticationPort.validateIdUser(authorizationHeader)).thenReturn(clientId);
//        when(productPort.findById(1)).thenReturn(product);
//        when(timeWaitingOrderQueueUseCase.execute(authorizationHeader)).thenReturn(10);
//        when(orderPort.save(Mockito.any(Order.class), authorizationHeader)).thenAnswer(invocation -> invocation.getArgument(0));
//
//        // Act
//        Order createdOrder = createOrderUseCase.createOrder(orderRequest, authorizationHeader);
//
//        // Assert
//        assertNotNull(createdOrder);
//        assertEquals(StatusOrder.WAITINGPAYMENT, createdOrder.getStatus());
//        assertEquals(20.0, createdOrder.getTotalPrice()); // 2 items * 10.0 each
//        assertEquals(20, createdOrder.getTimeWaitingOrder()); // 2 * 5 + 10
//        assertEquals(1, createdOrder.getOrderItemsList().size());
//
//        OrderItem orderItem = createdOrder.getOrderItemsList().get(0);
//        assertEquals(product.getDescription(), orderItem.getDescription());
//        assertEquals(10.0, orderItem.getTotalProductPrice());
//
//        verify(authenticationPort).validateAuthorizationHeader(authorizationHeader);
//        verify(authenticationPort).validateIdUser(authorizationHeader);
//        verify(productPort).findById(1);
//        verify(orderPort).save(createdOrder, authorizationHeader);
//    }
}
