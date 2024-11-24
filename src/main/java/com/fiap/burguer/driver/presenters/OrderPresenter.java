package com.fiap.burguer.driver.presenters;

import com.fiap.burguer.driver.dto.OrderResponse;
import com.fiap.burguer.core.domain.Order;
import com.fiap.burguer.core.domain.OrderItem;
import com.fiap.burguer.core.domain.Product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderPresenter {

    public static OrderResponse mapOrderToResponse(Order order) {
        if (order == null) {
            return null;
        }

        Date date = new Date();
        List<Product> products = new ArrayList<>();

        if (order.getOrderItemsList() != null) {
            for (OrderItem item : order.getOrderItemsList()) {
                if (item.getOrder() != null && item.getOrder().getDateCreated() != null) {
                    date = item.getOrder().getDateCreated();
                }
                products.add(item.getProduct());
            }
        }

        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setStatus(order.getStatus() != null ? order.getStatus().toString() : null);
        response.setTotalPrice(order.getTotalPrice());
        response.setTimeWaitingOrder(order.getTimeWaitingOrder());
        response.setDateCreated(date);
        response.setProducts(products);
        return response;
    }

}
