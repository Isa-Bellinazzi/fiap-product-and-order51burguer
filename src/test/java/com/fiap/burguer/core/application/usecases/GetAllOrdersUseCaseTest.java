package com.fiap.burguer.core.application.usecases;

import com.fiap.burguer.core.application.exception.ResourceNotFoundException;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.ports.AuthenticationPort;
import com.fiap.burguer.core.application.ports.OrderPort;
import com.fiap.burguer.core.domain.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetAllOrdersUseCaseTest {

    private OrderPort orderPort;
    private AuthenticationPort authenticationPort;
    private GetAllOrdersUseCase getAllOrdersUseCase;

    @BeforeEach
    void setUp() {
        orderPort = mock(OrderPort.class);
        authenticationPort = mock(AuthenticationPort.class);
        getAllOrdersUseCase = new GetAllOrdersUseCase(orderPort, authenticationPort);
    }

    @Test
    void shouldReturnAllOrdersSuccessfully() {
        Order order1 = new Order();
        order1.setId(1);
        order1.setStatus(StatusOrder.RECEIVED);

        Order order2 = new Order();
        order2.setId(2);
        order2.setStatus(StatusOrder.PREPARATION);

        when(orderPort.findAll()).thenReturn(List.of(order1, order2));

        List<Order> orders = getAllOrdersUseCase.getAllOrders();

        assertNotNull(orders);
        assertEquals(2, orders.size());
        verify(orderPort).findAll();
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenNoOrdersExist() {
        // Arrange
        when(orderPort.findAll()).thenReturn(List.of());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> getAllOrdersUseCase.getAllOrders());
        assertEquals("Não existem pedidos ainda", exception.getMessage());
        verify(orderPort).findAll();
    }

    @Test
    void shouldReturnCorrectStatusPriority() {
        assertEquals(1, getAllOrdersUseCase.getStatusPriority(StatusOrder.READY));
        assertEquals(2, getAllOrdersUseCase.getStatusPriority(StatusOrder.PREPARATION));
        assertEquals(3, getAllOrdersUseCase.getStatusPriority(StatusOrder.RECEIVED));
        assertEquals(Integer.MAX_VALUE, getAllOrdersUseCase.getStatusPriority(StatusOrder.WAITINGPAYMENT));
    }
}
