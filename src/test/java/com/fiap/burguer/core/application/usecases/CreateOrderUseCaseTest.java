package com.fiap.burguer.core.application.usecases;

import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.ports.AuthenticationPort;
import com.fiap.burguer.core.application.ports.OrderPort;
import com.fiap.burguer.core.application.ports.ProductPort;
import com.fiap.burguer.core.domain.Order;
import com.fiap.burguer.core.domain.OrderItem;
import com.fiap.burguer.core.domain.Product;
import com.fiap.burguer.driver.dto.OrderItemRequest;
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

    @Test
    void shouldCreateOrderSuccessfully() {
        String authorizationHeader = "Bearer token";
        int clientId = 1;
        Product product = new Product();
        product.setId(1);
        product.setPrice(10.0);
        product.setPreparationTime(10);
        product.setDescription("Burger");

        OrderItemRequest item = new OrderItemRequest();
        item.setProductId(1);
        item.setQuantity(1);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setItems(List.of(item));

        when(authenticationPort.validateIdUser(authorizationHeader)).thenReturn(clientId);
        when(productPort.findById(1)).thenReturn(product); // Mock product fetching
        when(timeWaitingOrderQueueUseCase.execute(authorizationHeader)).thenReturn(10); // Mock waiting time
        when(orderPort.save(Mockito.any(Order.class), eq(authorizationHeader))).thenAnswer(invocation -> invocation.getArgument(0)); // Mock save operation

        Order createdOrder = createOrderUseCase.createOrder(orderRequest, authorizationHeader);

        assertNotNull(createdOrder);
        assertEquals(StatusOrder.WAITINGPAYMENT, createdOrder.getStatus());
        assertEquals(10.0, createdOrder.getTotalPrice(), 0.001);
        assertEquals(20, createdOrder.getTimeWaitingOrder());

        assertEquals(1, createdOrder.getOrderItemsList().size());
        OrderItem orderItem = createdOrder.getOrderItemsList().getFirst();
        assertEquals(product.getDescription(), orderItem.getDescription());
        assertEquals(10.0, orderItem.getTotalProductPrice(), 0.001);

        verify(authenticationPort).validateAuthorizationHeader(authorizationHeader);
        verify(authenticationPort).validateIdUser(authorizationHeader);
        verify(productPort).findById(1);
        verify(orderPort).save(createdOrder, authorizationHeader);
    }

}
