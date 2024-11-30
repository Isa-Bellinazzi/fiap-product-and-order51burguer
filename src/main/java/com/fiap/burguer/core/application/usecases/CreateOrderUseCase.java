package com.fiap.burguer.core.application.usecases;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.ports.AuthenticationPort;
import com.fiap.burguer.core.application.ports.OrderPort;
import com.fiap.burguer.core.application.ports.ProductPort;
import com.fiap.burguer.core.domain.Order;
import com.fiap.burguer.core.domain.OrderItem;
import com.fiap.burguer.core.domain.Product;
import com.fiap.burguer.driver.dto.OrderRequest;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class CreateOrderUseCase {
    private final OrderPort orderPort;
    private final ValidateOrderUseCase validateOrderUseCase;
    private final ProductPort productPort;
    private final TimeWaitingOrderQueueUseCase timeWaitingOrderQueueUseCase;
    private final AuthenticationPort authenticationPort;

    public CreateOrderUseCase(OrderPort orderPort,
                              ValidateOrderUseCase validateOrderUseCase,
                              ProductPort productPort, TimeWaitingOrderQueueUseCase timeWaitingOrderQueueUseCase,
                              AuthenticationPort authenticationPort) {
        this.orderPort = orderPort;
        this.validateOrderUseCase = validateOrderUseCase;
        this.productPort = productPort;
        this.timeWaitingOrderQueueUseCase = timeWaitingOrderQueueUseCase;
        this.authenticationPort = authenticationPort;
    }

    public Order createOrder(OrderRequest orderRequest, String authorizationHeader) {
        authenticationPort.validateAuthorizationHeader(authorizationHeader);

        Integer clientId = authenticationPort.validateIdUser(authorizationHeader);

        if (clientId != null) {
            orderRequest.setIdClient(clientId);
        }

        validateOrderUseCase.execute(orderRequest);
        AtomicReference<Integer> timeOrder = new AtomicReference<>(0);
        Order order = new Order();
        order.setDateCreated(new Date());
        order.setStatus(StatusOrder.WAITINGPAYMENT);
        order.setTotalPrice(0.0);
        order.setTimeWaitingOrder(0);

        List<OrderItem> orderItems = makeOrderItemObjects(orderRequest, timeOrder, order);
        order.setTimeWaitingOrder(timeOrder.get() + timeWaitingOrderQueueUseCase.execute(authorizationHeader));
        order.setOrderItemsList(orderItems);
        return orderPort.save(order);
    }

    private List<OrderItem> makeOrderItemObjects(OrderRequest orderRequest, AtomicReference<Integer> timeOrder, Order order) {

        return orderRequest.getItems().stream().map(itemRequest -> {
            Optional<Product> optionalProduct = Optional.ofNullable(productPort.findById(itemRequest.getProductId()));
            Product product = optionalProduct.orElseThrow(() -> new RuntimeException(String.format("Produto %s não encontrado", itemRequest.getProductId())));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setAmount(itemRequest.getQuantity());
            orderItem.setTotalProductPrice(product.getPrice());
            orderItem.setPreparationTime(product.getPreparationTime().toString());
            orderItem.setDescription(product.getDescription());
            orderItem.setOrder(order);

            order.setTotalPrice(order.getTotalPrice() + (product.getPrice() * itemRequest.getQuantity()));
            timeOrder.updateAndGet(v -> v + order.getTimeWaitingOrder() + (product.getPreparationTime() * itemRequest.getQuantity()));


            return orderItem;
        }).toList();

    }

}
