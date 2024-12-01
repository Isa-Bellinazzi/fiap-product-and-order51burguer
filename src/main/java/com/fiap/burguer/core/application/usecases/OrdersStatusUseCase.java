package com.fiap.burguer.core.application.usecases;
import com.fiap.burguer.core.application.exception.RequestException;
import com.fiap.burguer.core.application.exception.ResourceNotFoundException;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.ports.AuthenticationPort;
import com.fiap.burguer.core.application.ports.OrderPort;
import com.fiap.burguer.core.domain.Order;

import java.util.List;

public class OrdersStatusUseCase {
    private final OrderPort orderPort;
    private final AuthenticationPort authenticationPort;

    public OrdersStatusUseCase(OrderPort orderPort, AuthenticationPort authenticationPort) {
        this.orderPort = orderPort;
        this.authenticationPort = authenticationPort;
    }

    public List<Order> getOrdersByStatus(StatusOrder status) {
        List<Order> orderEntities = orderPort.findByStatus(status);

        if (orderEntities == null || orderEntities.isEmpty()) {
            throw new ResourceNotFoundException("Não existem pedidos com esse status");
        }

        return orderEntities;
    }

    public void updateOrderStatus(Order order, StatusOrder newStatus, String authorizationHeader) {
        authenticationPort.validateAuthorizationHeader(authorizationHeader);
        authenticationPort.validateIsAdminAccess(authorizationHeader);

        boolean isValidUpdate = isValidStatusUpdate(order.getStatus(), newStatus);
        if (!isValidUpdate) {
            throw new RequestException("Atualização de status para: " + newStatus + " inválida");
        }
        order.setStatus(newStatus);
        orderPort.save(order);
    }

    public boolean isValidStatusUpdate(StatusOrder currentStatus, StatusOrder newStatus) {
        if (!isValidNextStatus(currentStatus, newStatus)) {
            return false;
        }

        if (newStatus == StatusOrder.CANCELED) {
            return isCancelValid(currentStatus);
        }

        return true;
    }

    public boolean isValidNextStatus(StatusOrder currentStatus, StatusOrder newStatus) {
        if (newStatus == StatusOrder.RECEIVED && currentStatus != StatusOrder.APPROVEDPAYMENT && currentStatus != StatusOrder.WAITINGPAYMENT) {
            return false;
        }
        return switch (currentStatus) {
            case WAITINGPAYMENT ->
                    newStatus == StatusOrder.APPROVEDPAYMENT ||
                    newStatus == StatusOrder.REJECTEDPAYMENT ||
                    newStatus == StatusOrder.CANCELED;
            case APPROVEDPAYMENT -> newStatus == StatusOrder.RECEIVED;
            case RECEIVED -> newStatus == StatusOrder.PREPARATION;
            case PREPARATION -> newStatus == StatusOrder.READY;
            case READY -> newStatus == StatusOrder.FINISHED;
            case REJECTEDPAYMENT -> newStatus == StatusOrder.CANCELED;
            default -> false;
        };
    }

    public boolean isCancelValid(StatusOrder currentStatus) {
        return currentStatus == StatusOrder.WAITINGPAYMENT || currentStatus == StatusOrder.REJECTEDPAYMENT;
    }



}
