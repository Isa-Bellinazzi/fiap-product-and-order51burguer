package com.fiap.burguer.core.application.usecases;

import com.fiap.burguer.core.application.exception.RequestException;
import com.fiap.burguer.core.application.exception.ResourceNotFoundException;
import com.fiap.burguer.core.application.ports.AuthenticationPort;
import com.fiap.burguer.core.application.ports.OrderPort;
import com.fiap.burguer.core.domain.Order;

public class GetOrderByIdUseCase {
    private final OrderPort orderPort;
    private final AuthenticationPort authenticationPort;

    public GetOrderByIdUseCase(OrderPort orderPort, AuthenticationPort authenticationPort) {
        this.orderPort = orderPort;
        this.authenticationPort = authenticationPort;
    }

    public Order getOrderById(int id, String authorizationHeader) {
        authenticationPort.validateAuthorizationHeader(authorizationHeader);
        if(id < 1 ) throw new RequestException("Id do Pedido inválido");
        Order order = orderPort.findById(id);
        if (order == null) {
            throw new ResourceNotFoundException("Pedido não encontrado");
        }
        return order;
    }
}
