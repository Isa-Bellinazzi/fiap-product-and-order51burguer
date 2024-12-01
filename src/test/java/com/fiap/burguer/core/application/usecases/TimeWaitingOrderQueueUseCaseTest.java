package com.fiap.burguer.core.application.usecases;

import com.fiap.burguer.core.application.exception.ResourceNotFoundException;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.domain.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TimeWaitingOrderQueueUseCaseTest {

    @Mock
    private OrdersStatusUseCase ordersStatusUseCase;

    private TimeWaitingOrderQueueUseCase timeWaitingOrderQueueUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        timeWaitingOrderQueueUseCase = new TimeWaitingOrderQueueUseCase(ordersStatusUseCase);
    }

    @Test
    void testExecute_Success() {
        String authorizationHeader = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjcGYiOiI3NzU4MjkzMDAwMiIsIm5hbWUiOiJNYXJpYSBOdW5lcyIsImlkIjoyLCJpc0FkbWluIjp0cnVlLCJleHAiOjE3MzQxOTM1MTgsImVtYWlsIjoibWFyaWFOdW5lc0BleGFtcGxlLmNvbSJ9.2mOK0LBKuy2lAXFrEuoUQxTvHzXq8ypDS8vnW-b3sD8";

        Order order1 = new Order();
        order1.setTimeWaitingOrder(10);

        Order order2 = new Order();
        order2.setTimeWaitingOrder(20);

        when(ordersStatusUseCase.getOrdersByStatus(StatusOrder.RECEIVED))
                .thenReturn(List.of(order1));
        when(ordersStatusUseCase.getOrdersByStatus(StatusOrder.PREPARATION))
                .thenReturn(List.of(order2));

        int result = timeWaitingOrderQueueUseCase.execute(authorizationHeader);

        verify(ordersStatusUseCase).getOrdersByStatus(StatusOrder.RECEIVED);
        verify(ordersStatusUseCase).getOrdersByStatus(StatusOrder.PREPARATION);

        assertEquals(30, result);
    }

    @Test
    void testExecute_NoOrdersFound() {
        String authorizationHeader = "Bearer validToken";

        when(ordersStatusUseCase.getOrdersByStatus(StatusOrder.RECEIVED))
                .thenThrow(new ResourceNotFoundException("No received orders found"));
        when(ordersStatusUseCase.getOrdersByStatus(StatusOrder.PREPARATION))
                .thenThrow(new ResourceNotFoundException("No preparation orders found"));

        int result = timeWaitingOrderQueueUseCase.execute(authorizationHeader);

        verify(ordersStatusUseCase).getOrdersByStatus(StatusOrder.RECEIVED);
        verify(ordersStatusUseCase).getOrdersByStatus(StatusOrder.PREPARATION);

        assertEquals(0, result);
    }

    @Test
    void testExecute_PartialOrdersFound() {
        String authorizationHeader = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjcGYiOiI3NzU4MjkzMDAwMiIsIm5hbWUiOiJNYXJpYSBOdW5lcyIsImlkIjoyLCJpc0FkbWluIjp0cnVlLCJleHAiOjE3MzQxOTM1MTgsImVtYWlsIjoibWFyaWFOdW5lc0BleGFtcGxlLmNvbSJ9.2mOK0LBKuy2lAXFrEuoUQxTvHzXq8ypDS8vnW-b3sD8";

        Order order = new Order();
        order.setTimeWaitingOrder(15);

        when(ordersStatusUseCase.getOrdersByStatus(StatusOrder.RECEIVED))
                .thenReturn(List.of(order));
        when(ordersStatusUseCase.getOrdersByStatus(StatusOrder.PREPARATION))
                .thenThrow(new ResourceNotFoundException("No preparation orders found"));

        int result = timeWaitingOrderQueueUseCase.execute(authorizationHeader);

        verify(ordersStatusUseCase).getOrdersByStatus(StatusOrder.RECEIVED);
        verify(ordersStatusUseCase).getOrdersByStatus(StatusOrder.PREPARATION);

        assertEquals(order.getTimeWaitingOrder(), result);
    }


    @Test
    void testSumTimeWaitingOrder_EmptyList() {
        int result = timeWaitingOrderQueueUseCase.execute("Bearer emptyToken");

        assertEquals(0, result);
    }
}
