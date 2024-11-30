package com.fiap.burguer.driver.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.burguer.IntegrationTest;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.application.usecases.*;
import com.fiap.burguer.core.domain.Order;
import com.fiap.burguer.driver.dto.OrderRequest;
import com.fiap.burguer.driver.handlers.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
 class OrderControllerTest extends IntegrationTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private CreateOrderUseCase createOrderUseCase;

    @Mock
    private GetAllOrdersUseCase getAllOrdersUseCase;

    @Mock
    private OrdersStatusUseCase ordersStatusUseCase;

    @Mock
    private GetOrderByIdUseCase getOrderByIdUseCase;

    private OrderRequest orderRequest;
    private Order order;

     @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        orderRequest = new OrderRequest();
        order = getOrderMock();


         orderController = new OrderController( createOrderUseCase, getAllOrdersUseCase, ordersStatusUseCase, getOrderByIdUseCase);
        mvc = MockMvcBuilders.standaloneSetup(orderController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    private Order getOrderMock() {
        order = new Order();
        order.setStatus(StatusOrder.READY);
        order.setOrderItemsList(List.of());
        order.setTimeWaitingOrder(0);
        order.setTotalPrice(0.0);
        order.setId(1);
        return order;
    }

    @Test
     void testCreateOrder() throws Exception {
        when(createOrderUseCase.createOrder(any(), any())).thenReturn(order);
        String json = new ObjectMapper().writeValueAsString(orderRequest);
        mvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
     void testGetAllOrders() throws Exception {
        List<Order> orders = Arrays.asList(order);
        when(getAllOrdersUseCase.getAllOrders(any())).thenReturn(orders);
        mvc.perform(get("/orders").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
     void testGetOrdersByStatus() throws Exception {
        List<Order> orders = Arrays.asList(order);
        when(ordersStatusUseCase.getOrdersByStatus(any(), any())).thenReturn(orders);
        mvc.perform(get("/orders/status/"+StatusOrder.READY).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
     void testGetOrderById() throws Exception {
        when(getOrderByIdUseCase.getOrderById(1, "authorizationHeader")).thenReturn(order);
        mvc.perform(get("/orders/"+1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
     void testUpdateOrderStatus() throws Exception {
        when(getOrderByIdUseCase.getOrderById(1, "authorizationHeader")).thenReturn(this.order);

        mvc.perform(put("/orders/"+1+"/status")
                        .param("newStatus", String.valueOf(StatusOrder.READY))
                        .header("Authorization", "authorizationHeader"))
                .andExpect(status().isOk());
    }

}