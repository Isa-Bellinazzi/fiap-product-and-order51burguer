package com.fiap.burguer.core.application.usecases;

import com.fiap.burguer.core.application.Exception.ResourceNotFoundException;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.ports.AuthenticationPort;
import com.fiap.burguer.core.domain.Order;

import java.util.List;

public class TimeWaitingOrderQueueUseCase {
    private final OrdersStatusUseCase getOrdersByStatusUseCase;

    public TimeWaitingOrderQueueUseCase(OrdersStatusUseCase getOrdersByStatusUseCase) {
        this.getOrdersByStatusUseCase = getOrdersByStatusUseCase;
    }

    public int execute(String authorizationHeader) {
        int totalWaitingTime = 0;
        try {
            List<Order> receivedOrderEntities = getOrdersByStatusUseCase.getOrdersByStatus(StatusOrder.RECEIVED, authorizationHeader);
            totalWaitingTime += sumTimeWaitingOrder(receivedOrderEntities);
        } catch (ResourceNotFoundException ignored) {
        }

        try {
            List<Order> preparationOrderEntities = getOrdersByStatusUseCase.getOrdersByStatus(StatusOrder.PREPARATION, authorizationHeader);
            totalWaitingTime += sumTimeWaitingOrder(preparationOrderEntities);
        } catch (ResourceNotFoundException ignored) {
        }

        return totalWaitingTime;
    }

    private int sumTimeWaitingOrder(List<Order> orders) {
        return orders.stream().mapToInt(Order::getTimeWaitingOrder).sum();
    }
}