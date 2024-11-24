package com.fiap.burguer.driver.dto;
import com.fiap.burguer.core.domain.Product;
import java.util.Date;
import java.util.List;

public class OrderResponse {
    private int id;
    private String status;
    private double totalPrice;
    private Date dateCreated;
    private double timeWaitingOrder;
    private List<Product> products;

    public OrderResponse(int id, String status, double totalPrice, Date dateCreated, double timeWaitingOrder,  List<Product> products) {
        this.id = id;
        this.status = status;
        this.totalPrice = totalPrice;
        this.dateCreated = dateCreated;
        this.timeWaitingOrder = timeWaitingOrder;
        this.products = products;
    }

    public OrderResponse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public double getTimeWaitingOrder() {
        return timeWaitingOrder;
    }

    public void setTimeWaitingOrder(double timeWaitingOrder) {
        this.timeWaitingOrder = timeWaitingOrder;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
