package com.fiap.burguer.core.application.usecases;

import com.fiap.burguer.core.application.exception.RequestException;
import com.fiap.burguer.driver.dto.OrderItemRequest;
import com.fiap.burguer.driver.dto.OrderRequest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidateOrderUseCaseTest {

    private final ValidateOrderUseCase validateOrderUseCase = new ValidateOrderUseCase();

    @Test
    void testExecute_ValidOrder() {
        OrderRequest orderRequest = new OrderRequest();
        OrderItemRequest item1 = new OrderItemRequest();
        item1.setProductId(1);
        item1.setQuantity(2);

        OrderItemRequest item2 = new OrderItemRequest();
        item2.setProductId(2);
        item2.setQuantity(1);

        orderRequest.setItems(List.of(item1, item2));

        assertDoesNotThrow(() -> validateOrderUseCase.execute(orderRequest));
    }

    @Test
    void testExecute_InvalidOrder_ZeroQuantity() {
        OrderRequest orderRequest = new OrderRequest();
        OrderItemRequest item = new OrderItemRequest();
        item.setProductId(1);
        item.setQuantity(0);

        orderRequest.setItems(List.of(item));

        RequestException exception = assertThrows(RequestException.class,
                () -> validateOrderUseCase.execute(orderRequest));
        assertEquals("A quantidade do item com ID 1 deve ser maior que zero.", exception.getMessage());
    }

    @Test
    void testExecute_InvalidOrder_NegativeQuantity() {
        OrderRequest orderRequest = new OrderRequest();
        OrderItemRequest item = new OrderItemRequest();
        item.setProductId(1);
        item.setQuantity(-5);

        orderRequest.setItems(List.of(item));

        RequestException exception = assertThrows(RequestException.class,
                () -> validateOrderUseCase.execute(orderRequest));
        assertEquals("A quantidade do item com ID 1 deve ser maior que zero.", exception.getMessage());
    }

    @Test
    void testExecute_EmptyOrder() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setItems(List.of());
        assertDoesNotThrow(() -> validateOrderUseCase.execute(orderRequest));
    }

    @Test
    void testExecute_NullItems() {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setItems(null);

        assertDoesNotThrow(() -> validateOrderUseCase.execute(orderRequest));
    }
}
