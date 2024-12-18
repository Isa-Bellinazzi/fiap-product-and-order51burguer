package com.fiap.burguer.driver.dto;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class OrderRequest {
    @JsonIgnore
    private Integer idClient;

    private List<OrderItemRequest> items;

    public OrderRequest() {
    }

    public OrderRequest(Integer idClient, List<OrderItemRequest> items) {
        this.idClient = idClient;
        this.items = items;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }


}
