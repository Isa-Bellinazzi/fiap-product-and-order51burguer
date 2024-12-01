package com.fiap.burguer.core.application.usecases;
import com.fiap.burguer.core.application.exception.ResourceNotFoundException;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.ports.AuthenticationPort;
import com.fiap.burguer.core.application.ports.OrderPort;
import com.fiap.burguer.core.domain.Order;
import java.util.List;

public class GetAllOrdersUseCase {
    private final OrderPort orderPort;
    private final AuthenticationPort authenticationPort;
    public GetAllOrdersUseCase(OrderPort orderPort, AuthenticationPort authenticationPort) {
        this.orderPort = orderPort;
        this.authenticationPort = authenticationPort;
    }

    public List<Order> getAllOrders() {

        List<Order> orderEntities = orderPort.findAll();

        if (orderEntities == null || orderEntities.isEmpty()) {
            throw new ResourceNotFoundException("Não existem pedidos ainda");
        }

        return orderEntities;
    }


    protected int getStatusPriority(StatusOrder status) {
        return switch (status) {
            case READY -> 1;
            case PREPARATION -> 2;
            case RECEIVED -> 3;
            default -> Integer.MAX_VALUE;
        };
    }
}
