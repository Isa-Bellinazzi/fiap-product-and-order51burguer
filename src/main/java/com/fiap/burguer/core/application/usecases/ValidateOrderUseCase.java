package com.fiap.burguer.core.application.usecases;
import com.fiap.burguer.core.application.exception.RequestException;
import com.fiap.burguer.driver.dto.OrderItemRequest;
import com.fiap.burguer.driver.dto.OrderRequest;

public class ValidateOrderUseCase {
    public void execute(OrderRequest orderRequest) {
        if (orderRequest.getItems() != null) {
            for (OrderItemRequest item : orderRequest.getItems()) {
                if (item.getQuantity() <= 0) {
                    String errorMessage = "A quantidade do item com ID " + item.getProductId() + " deve ser maior que zero.";
                    throw new RequestException(errorMessage);
                }
            }
        }
    }
}
