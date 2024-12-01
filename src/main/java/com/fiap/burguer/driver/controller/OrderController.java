package com.fiap.burguer.driver.controller;
import com.fiap.burguer.api.OrderApi;
import com.fiap.burguer.core.application.usecases.*;
import com.fiap.burguer.driver.presenters.OrderPresenter;
import com.fiap.burguer.driver.dto.OrderRequest;
import com.fiap.burguer.driver.dto.OrderResponse;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.domain.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class OrderController implements OrderApi {
    private final CreateOrderUseCase createOrderUseCase;
    private final GetAllOrdersUseCase getAllOrdersUseCase;
    private final OrdersStatusUseCase ordersByStatusUseCase;
    private final GetOrderByIdUseCase getOrderByIdUseCase;

    public OrderController(CreateOrderUseCase createOrder , GetAllOrdersUseCase getAllOrders, OrdersStatusUseCase getOrdersByStatus, GetOrderByIdUseCase getOrderById ) {
        this.createOrderUseCase = createOrder;
        this.getAllOrdersUseCase = getAllOrders;
        this.ordersByStatusUseCase = getOrdersByStatus;
        this.getOrderByIdUseCase = getOrderById;
    }

    public ResponseEntity<OrderResponse> createOrder (OrderRequest orderRequest, String authorizationHeader) {

        Order order = createOrderUseCase.createOrder(orderRequest, authorizationHeader);
        OrderResponse response = OrderPresenter.mapOrderToResponse(order);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<List<OrderResponse>> getAllOrders( ) {
        List<Order> orderEntities = getAllOrdersUseCase.getAllOrders();

        List<OrderResponse> responses = orderEntities.stream()
                .map(OrderPresenter::mapOrderToResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }


    public ResponseEntity<List<OrderResponse>> getOrdersByStatus(StatusOrder status) {
        List<Order> orders = ordersByStatusUseCase.getOrdersByStatus(status);

        List<OrderResponse> responses = orders.stream()
                .map(OrderPresenter::mapOrderToResponse)
                .toList();

        return ResponseEntity.ok(responses);
    }

    public  ResponseEntity<OrderResponse> getOrderById(int id) {
        Order order = getOrderByIdUseCase.getOrderById(id);
        OrderResponse response = OrderPresenter.mapOrderToResponse(order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public ResponseEntity<OrderResponse> updateOrderStatus(int id, StatusOrder newStatus, String authorizationHeader) {

        Order order = getOrderByIdUseCase.getOrderById(id);

        if(newStatus == StatusOrder.APPROVEDPAYMENT){
            ordersByStatusUseCase.updateOrderStatus(order, newStatus, authorizationHeader);
            newStatus = StatusOrder.RECEIVED;
        }
        order.setStatus(StatusOrder.APPROVEDPAYMENT);
        ordersByStatusUseCase.updateOrderStatus(order, newStatus, authorizationHeader);
        OrderResponse response = OrderPresenter.mapOrderToResponse(order);
        return ResponseEntity.ok(response);
    }



}
