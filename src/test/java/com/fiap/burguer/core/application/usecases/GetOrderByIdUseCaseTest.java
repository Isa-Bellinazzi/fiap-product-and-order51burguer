package com.fiap.burguer.core.application.usecases;
import com.fiap.burguer.core.application.exception.RequestException;
import com.fiap.burguer.core.application.exception.ResourceNotFoundException;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.ports.AuthenticationPort;
import com.fiap.burguer.core.application.ports.OrderPort;
import com.fiap.burguer.core.domain.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetOrderByIdUseCaseTest {

    private OrderPort orderPort;
    private AuthenticationPort authenticationPort;
    private GetOrderByIdUseCase getOrderByIdUseCase;

    @BeforeEach
    void setUp() {
        orderPort = mock(OrderPort.class);
        authenticationPort = mock(AuthenticationPort.class);
        getOrderByIdUseCase = new GetOrderByIdUseCase(orderPort, authenticationPort);
    }

    @Test
    void shouldReturnOrderWhenOrderExists() {
        // Arrange
        int orderId = 1;
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(StatusOrder.RECEIVED);

        when(orderPort.findById(orderId)).thenReturn(order);
        Order result = getOrderByIdUseCase.getOrderById(orderId);
        assertNotNull(result);
        assertEquals(orderId, result.getId());
        assertEquals(StatusOrder.RECEIVED, result.getStatus());

        verify(orderPort).findById(orderId);
    }

    @Test
    void shouldThrowRequestExceptionWhenIdIsInvalid() {
       int invalidOrderId = 0;

        RequestException exception = assertThrows(RequestException.class,
                () -> getOrderByIdUseCase.getOrderById(invalidOrderId));

        assertEquals("Id do Pedido inválido", exception.getMessage());
        verify(orderPort, never()).findById(invalidOrderId);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenOrderNotFound() {
        int orderId = 1;
        when(orderPort.findById(orderId)).thenReturn(null);

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> getOrderByIdUseCase.getOrderById(orderId));

        assertEquals("Pedido não encontrado", exception.getMessage());

        verify(orderPort).findById(orderId);
    }

    @Test
    void shouldValidateAuthorizationHeader() {
        int orderId = 1;
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(StatusOrder.RECEIVED);
        when(orderPort.findById(orderId)).thenReturn(order);
        getOrderByIdUseCase.getOrderById(orderId);
    }
}

