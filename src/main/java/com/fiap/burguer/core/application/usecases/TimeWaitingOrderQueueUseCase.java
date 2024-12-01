package com.fiap.burguer.core.application.usecases;
import com.fiap.burguer.core.application.exception.ResourceNotFoundException;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.domain.Order;
import java.util.List;
import java.util.logging.Logger;

public class TimeWaitingOrderQueueUseCase {
    private static final Logger logger = Logger.getLogger(TimeWaitingOrderQueueUseCase.class.getName());
    private final OrdersStatusUseCase getOrdersByStatusUseCase;

    public TimeWaitingOrderQueueUseCase(OrdersStatusUseCase getOrdersByStatusUseCase) {
        this.getOrdersByStatusUseCase = getOrdersByStatusUseCase;
    }

    public int execute(String authorizationHeader) {
        int totalWaitingTime = 0;

        try {
            List<Order> receivedOrderEntities = getOrdersByStatusUseCase.getOrdersByStatus(StatusOrder.RECEIVED);
            totalWaitingTime += sumTimeWaitingOrder(receivedOrderEntities);
        } catch (ResourceNotFoundException e) {
            logger.warning("No orders found with status RECEIVED: " + e.getMessage());
        }

        try {
            List<Order> preparationOrderEntities = getOrdersByStatusUseCase.getOrdersByStatus(StatusOrder.PREPARATION);
            totalWaitingTime += sumTimeWaitingOrder(preparationOrderEntities);
        } catch (ResourceNotFoundException e) {
            logger.warning("No orders found with status PREPARATION: " + e.getMessage());
        }

        return totalWaitingTime;
    }

    private int sumTimeWaitingOrder(List<Order> orders) {
        return orders.stream().mapToInt(Order::getTimeWaitingOrder).sum();
    }
}
