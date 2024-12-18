package com.fiap.burguer.core.application.usecases;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import com.fiap.burguer.core.application.exception.RequestException;
import com.fiap.burguer.core.application.exception.ResourceNotFoundException;
import com.fiap.burguer.core.application.ports.AuthenticationPort;
import com.fiap.burguer.core.application.ports.OrderPort;
import com.fiap.burguer.core.domain.Order;
import com.fiap.burguer.core.application.enums.StatusOrder;
import java.util.List;
import java.util.Collections;

 class OrdersStatusUseCaseTest{

    @Mock
    private OrderPort orderPort;

    @Mock
    private AuthenticationPort authenticationPort;

    private OrdersStatusUseCase ordersStatusUseCase;

    String authorization = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjcGYiOiI3NzU4MjkzMDAwMiIsIm5hbWUiOiJNYXJpYSBOdW5lcyIsImlkIjoyLCJpc0FkbWluIjp0cnVlLCJleHAiOjE3MzQxOTM1MTgsImVtYWlsIjoibWFyaWFOdW5lc0BleGFtcGxlLmNvbSJ9.2mOK0LBKuy2lAXFrEuoUQxTvHzXq8ypDS8vnW-b3sD8";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ordersStatusUseCase = new OrdersStatusUseCase(orderPort, authenticationPort);
    }

    @Test
    void testGetOrdersByStatusValidAuthorization() {
        StatusOrder status = StatusOrder.WAITINGPAYMENT;
        List<Order> orders = List.of(new Order());
        when(orderPort.findByStatus(status)).thenReturn(orders);
        List<Order> result = ordersStatusUseCase.getOrdersByStatus(status);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testGetOrdersByStatusEmptyListThrowsException() {
        StatusOrder status = StatusOrder.WAITINGPAYMENT;
        when(orderPort.findByStatus(status)).thenReturn(Collections.emptyList());
        assertThrows(ResourceNotFoundException.class, () -> {
            ordersStatusUseCase.getOrdersByStatus(status);
        });
    }


    @Test
    void testUpdateOrderStatusValid() {
        Order order = new Order();
        order.setStatus(StatusOrder.RECEIVED);
        StatusOrder newStatus = StatusOrder.PREPARATION;
        when(orderPort.save(order, authorization)).thenReturn(order);
        ordersStatusUseCase.updateOrderStatus(order, newStatus, authorization);
        assertEquals(newStatus, order.getStatus());
    }

    @Test
    void testUpdateOrderStatusInvalidTransition() {
        Order order = new Order();
        order.setStatus(StatusOrder.WAITINGPAYMENT);

        StatusOrder newStatus = StatusOrder.READY;

        assertThrows(RequestException.class, () -> {
            ordersStatusUseCase.updateOrderStatus(order, newStatus, authorization);
        });
    }

    @Test
    void testIsValidStatusUpdateValidTransition() {
        StatusOrder currentStatus = StatusOrder.WAITINGPAYMENT;
        StatusOrder newStatus = StatusOrder.APPROVEDPAYMENT;

        boolean result = ordersStatusUseCase.isValidStatusUpdate(currentStatus, newStatus);

        assertTrue(result);
    }

    @Test
    void testIsValidStatusUpdateInvalidTransition() {
        StatusOrder currentStatus = StatusOrder.PREPARATION;
        StatusOrder newStatus = StatusOrder.WAITINGPAYMENT;

        boolean result = ordersStatusUseCase.isValidStatusUpdate(currentStatus, newStatus);

        assertFalse(result);
    }

    @Test
    void testIsValidStatusUpdateCancelValid() {
        StatusOrder currentStatus = StatusOrder.WAITINGPAYMENT;
        StatusOrder newStatus = StatusOrder.CANCELED;

        boolean result = ordersStatusUseCase.isValidStatusUpdate(currentStatus, newStatus);

        assertTrue(result);
    }

    @Test
    void testIsValidStatusUpdateCancelInvalid() {
        StatusOrder currentStatus = StatusOrder.READY;
        StatusOrder newStatus = StatusOrder.CANCELED;

        boolean result = ordersStatusUseCase.isValidStatusUpdate(currentStatus, newStatus);

        assertFalse(result);
    }

    @Test
    void testIsValidStatusUpdateReceivedInvalid() {
        StatusOrder currentStatus = StatusOrder.WAITINGPAYMENT;
        StatusOrder newStatus = StatusOrder.RECEIVED;

        boolean result = ordersStatusUseCase.isValidStatusUpdate(currentStatus, newStatus);

        assertFalse(result);
    }


    @Test
    void testIsValidNextStatusValidWaitingPaymentToApprovedPayment() {
        StatusOrder currentStatus = StatusOrder.WAITINGPAYMENT;
        StatusOrder newStatus = StatusOrder.APPROVEDPAYMENT;

        boolean result = ordersStatusUseCase.isValidNextStatus(currentStatus, newStatus);

        assertTrue(result);  // Esperado: Transição válida de WAITINGPAYMENT para APPROVEDPAYMENT
    }

    @Test
    void testIsValidNextStatusValidWaitingPaymentToRejectedPayment() {
        StatusOrder currentStatus = StatusOrder.WAITINGPAYMENT;
        StatusOrder newStatus = StatusOrder.REJECTEDPAYMENT;

        boolean result = ordersStatusUseCase.isValidNextStatus(currentStatus, newStatus);

        assertTrue(result);  // Esperado: Transição válida de WAITINGPAYMENT para REJECTEDPAYMENT
    }

    @Test
    void testIsValidNextStatusValidWaitingPaymentToCanceled() {
        StatusOrder currentStatus = StatusOrder.WAITINGPAYMENT;
        StatusOrder newStatus = StatusOrder.CANCELED;

        boolean result = ordersStatusUseCase.isValidNextStatus(currentStatus, newStatus);

        assertTrue(result);  // Esperado: Transição válida de WAITINGPAYMENT para CANCELED
    }
// Aqui 2


    @Test
    void testIsValidNextStatusValidReceivedToPreparation() {
        StatusOrder currentStatus = StatusOrder.RECEIVED;
        StatusOrder newStatus = StatusOrder.PREPARATION;

        boolean result = ordersStatusUseCase.isValidNextStatus(currentStatus, newStatus);

        assertTrue(result);  // Esperado: Transição válida de RECEIVED para PREPARATION
    }


    // Aqui 3

    @Test
    void testIsValidNextStatusValidPreparationToReady() {
        StatusOrder currentStatus = StatusOrder.PREPARATION;
        StatusOrder newStatus = StatusOrder.READY;

        boolean result = ordersStatusUseCase.isValidNextStatus(currentStatus, newStatus);

        assertTrue(result);  // Esperado: Transição válida de PREPARATION para READY
    }

    // Aqui 4

    @Test
    void testIsValidNextStatusValidReadyToFinished() {
        StatusOrder currentStatus = StatusOrder.READY;
        StatusOrder newStatus = StatusOrder.FINISHED;

        boolean result = ordersStatusUseCase.isValidNextStatus(currentStatus, newStatus);

        assertTrue(result);  // Esperado: Transição válida de READY para FINISHED
    }

    // Aqui 5

    @Test
    void testIsValidNextStatusValidRejectedPaymentToCanceled() {
        StatusOrder currentStatus = StatusOrder.REJECTEDPAYMENT;
        StatusOrder newStatus = StatusOrder.CANCELED;

        boolean result = ordersStatusUseCase.isValidNextStatus(currentStatus, newStatus);

        assertTrue(result);  // Esperado: Transição válida de REJECTEDPAYMENT para CANCELED
    }


    // Aqui

    @Test
    void testIsValidNextStatusInvalidFromWaitingPaymentToPreparation() {
        StatusOrder currentStatus = StatusOrder.WAITINGPAYMENT;
        StatusOrder newStatus = StatusOrder.PREPARATION;

        boolean result = ordersStatusUseCase.isValidNextStatus(currentStatus, newStatus);

        assertFalse(result);  // Esperado: Transição inválida de WAITINGPAYMENT para PREPARATION
    }

    @Test
    void testIsValidNextStatusInvalidFromReceivedToApprovedPayment() {
        StatusOrder currentStatus = StatusOrder.RECEIVED;
        StatusOrder newStatus = StatusOrder.APPROVEDPAYMENT;

        boolean result = ordersStatusUseCase.isValidNextStatus(currentStatus, newStatus);

        assertFalse(result);  // Esperado: Transição inválida de RECEIVED para APPROVEDPAYMENT
    }

    @Test
    void testIsValidNextStatusInvalidFromPreparationToWaitingPayment() {
        StatusOrder currentStatus = StatusOrder.PREPARATION;
        StatusOrder newStatus = StatusOrder.WAITINGPAYMENT;

        boolean result = ordersStatusUseCase.isValidNextStatus(currentStatus, newStatus);

        assertFalse(result);  // Esperado: Transição inválida de PREPARATION para WAITINGPAYMENT
    }

    @Test
    void testIsValidNextStatusInvalidFromReadyToRejectedPayment() {
        StatusOrder currentStatus = StatusOrder.READY;
        StatusOrder newStatus = StatusOrder.REJECTEDPAYMENT;

        boolean result = ordersStatusUseCase.isValidNextStatus(currentStatus, newStatus);

        assertFalse(result);  // Esperado: Transição inválida de READY para REJECTEDPAYMENT
    }

    @Test
    void testIsValidNextStatusInvalidFromRejectedPaymentToReady() {
        StatusOrder currentStatus = StatusOrder.REJECTEDPAYMENT;
        StatusOrder newStatus = StatusOrder.READY;

        boolean result = ordersStatusUseCase.isValidNextStatus(currentStatus, newStatus);

        assertFalse(result);  // Esperado: Transição inválida de REJECTEDPAYMENT para READY
    }

    @Test
    void testIsValidNextStatusInvalidFromCanceledToAny() {
        StatusOrder currentStatus = StatusOrder.CANCELED;
        StatusOrder newStatus = StatusOrder.PREPARATION;

        boolean result = ordersStatusUseCase.isValidNextStatus(currentStatus, newStatus);

        assertFalse(result);  // Esperado: Transição inválida de CANCELED para qualquer outro status
    }

    @Test
    void testIsCancelValidValidWaitingPayment() {
        StatusOrder currentStatus = StatusOrder.WAITINGPAYMENT;

        boolean result = ordersStatusUseCase.isCancelValid(currentStatus);

        assertTrue(result);  // Esperado: CANCELAMENTO válido para WAITINGPAYMENT
    }



    @Test
    void testIsCancelValidValidRejectedPayment() {
        StatusOrder currentStatus = StatusOrder.REJECTEDPAYMENT;

        boolean result = ordersStatusUseCase.isCancelValid(currentStatus);

        assertTrue(result);  // Esperado: CANCELAMENTO válido para REJECTEDPAYMENT
    }


    @Test
    void testIsCancelValidInvalidApprovedPayment() {
        StatusOrder currentStatus = StatusOrder.APPROVEDPAYMENT;

        boolean result = ordersStatusUseCase.isCancelValid(currentStatus);

        assertFalse(result);  // Esperado: CANCELAMENTO inválido para APPROVEDPAYMENT
    }


    @Test
    void testIsCancelValidInvalidReceived() {
        StatusOrder currentStatus = StatusOrder.RECEIVED;

        boolean result = ordersStatusUseCase.isCancelValid(currentStatus);

        assertFalse(result);  // Esperado: CANCELAMENTO inválido para RECEIVED
    }


    @Test
    void testIsCancelValidInvalidPreparation() {
        StatusOrder currentStatus = StatusOrder.PREPARATION;

        boolean result = ordersStatusUseCase.isCancelValid(currentStatus);

        assertFalse(result);  // Esperado: CANCELAMENTO inválido para PREPARATION
    }

    @Test
    void testIsCancelValidInvalidReady() {
        StatusOrder currentStatus = StatusOrder.READY;

        boolean result = ordersStatusUseCase.isCancelValid(currentStatus);

        assertFalse(result);  
    }

    @Test
    void testIsCancelValidInvalidFinished() {
        StatusOrder currentStatus = StatusOrder.FINISHED;

        boolean result = ordersStatusUseCase.isCancelValid(currentStatus);

        assertFalse(result);
    }

     @Test
     void shouldReturnFalseWhenStatusUpdateIsInvalidFromNonApprovedPaymentToReceived() {
         StatusOrder currentStatus = StatusOrder.WAITINGPAYMENT;
         StatusOrder newStatus = StatusOrder.RECEIVED;
         boolean result = ordersStatusUseCase.isValidStatusUpdate(currentStatus, newStatus);
         assertFalse(result, "A transição de status de 'WAITINGPAYMENT' para 'RECEIVED' não deve ser válida.");
     }
}
