package com.fiap.burguer.config;
import com.fiap.burguer.core.application.usecases.*;
import com.fiap.burguer.infraestructure.adapters.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Autowired
    ProductAdapter productAdapter;

    @Autowired
    OrderAdapter orderAdapter;

    @Autowired
    AuthenticationAdapter authenticationAdapter;

    @Bean
    public ProductUseCases getProductService() {
        return new ProductUseCases(productAdapter, authenticationAdapter);
    }

    @Bean
    public ValidateOrderUseCase validateOrderUseCase() {
        return new ValidateOrderUseCase();
    }


    @Bean
    public CreateOrderUseCase createOrderUseCase() {
        return new CreateOrderUseCase(orderAdapter,validateOrderUseCase(),productAdapter,timeWaitingOrderQueueUseCase() ,authenticationAdapter);
    }

    @Bean
    public TimeWaitingOrderQueueUseCase timeWaitingOrderQueueUseCase() {
        return new TimeWaitingOrderQueueUseCase(getOrdersByStatusUseCase());
    }

    @Bean
    public GetOrderByIdUseCase getOrderByIdUseCase() {
        return new GetOrderByIdUseCase(orderAdapter, authenticationAdapter);
    }

    @Bean
    public GetAllOrdersUseCase getAllOrdersUseCase() {
        return new GetAllOrdersUseCase(orderAdapter, authenticationAdapter);
    }

    @Bean
    public OrdersStatusUseCase getOrdersByStatusUseCase() {
        return new OrdersStatusUseCase(orderAdapter, authenticationAdapter);
    }
}
