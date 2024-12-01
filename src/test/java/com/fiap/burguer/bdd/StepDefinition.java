package com.fiap.burguer.bdd;


import com.fiap.burguer.core.application.enums.CategoryProduct;
import com.fiap.burguer.core.application.enums.StatusOrder;
import com.fiap.burguer.core.domain.Product;
import com.fiap.burguer.driver.dto.*;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class StepDefinition extends CucumberContext {

    private Response response;

    private OrderResponse orderResponse;

    private String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjcGYiOiI3NzU4MjkzMDAwMiIsIm5hbWUiOiJNYXJpYSBOdW5lcyIsImlkIjoyLCJpc0FkbWluIjp0cnVlLCJleHAiOjE3MzQxOTM1MTgsImVtYWlsIjoibWFyaWFOdW5lc0BleGFtcGxlLmNvbSJ9.2mOK0LBKuy2lAXFrEuoUQxTvHzXq8ypDS8vnW-b3sD8";

    private String ENDPOINT_BASE_ORDER = "http://localhost:8080/orders";

    private String ENDPOINT_BASE_PRODUCT = "http://localhost:8080/products";

    @Dado("que exista um produto cadastrado")
    public void queOUsuárioPreenchaOsItensDoPedido() {
        ProductCreate product = new ProductCreate("water","http://gangstaburguer.com/images/water.jpg", 2, "Agua cara", 15.0, CategoryProduct.DRINK);

        Product  productResponse = given().headers(
                        "Authorization",
                        token,
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                .body(product).when().post(ENDPOINT_BASE_PRODUCT).then().extract().as(Product.class);

        Assert.assertNotNull(productResponse);

    }


    @Quando("o usuário submeter o pedido para criacao")
    public void oUsuárioSubmeterOPedido() {
        createOrder();
    }

    private void createOrder() {
        OrderRequest orderRequest = new OrderRequest();
        OrderItemRequest orderItemRequest = new OrderItemRequest();
        orderItemRequest.setProductId(1);
        orderItemRequest.setQuantity(2);
        orderRequest.setItems(List.of(orderItemRequest));
        response = given().contentType(MediaType.APPLICATION_JSON_VALUE).headers(
                        "Authorization",
                        token,
                        "Accept",
                        ContentType.JSON)
                .body(orderRequest).when().post(ENDPOINT_BASE_ORDER);
    }

    @Entao("o sistema deve salvar o pedido")
    public void oSistemaDeveSalvarOPedido() {
        response.then()
                .statusCode(HttpStatus.OK.value());
    }

    @E("o sistema deve retornar os dados do pedido")
    public void oSistemaDeveRetornarOsDadosDoPedido() {
        orderResponse = response.then().extract().as(OrderResponse.class);
        Assert.assertNotNull(orderResponse);
    }

    @Dado("que existe um pedido na base de dados em preparação")
    public void queExisteUmPedidoNaBaseDeDadosEmPreparacao() {
        createOrder();
        orderResponse = response.then().extract().as(OrderResponse.class);
        updateStatusOrder(orderResponse.getId(),StatusOrder.APPROVEDPAYMENT);
        updateStatusOrder(orderResponse.getId(),StatusOrder.RECEIVED);
    }

    @Quando("o cliente consultar os pedidos")
    public void oClienteConsultarOsPedidos() {
        response = given().get(ENDPOINT_BASE_ORDER);
    }

    @Entao("o sistema deve retornar todos os pedidos em preparação")
    public void oSistemaDeveRetornarOsPedidos() {
        List<OrderResponse> ordersList = Arrays.stream(response.then().extract().as(OrderResponse[].class)).toList();
        Assert.assertNotNull(ordersList);
        Assert.assertFalse(ordersList.isEmpty());
    }

    private void updateStatusOrder(int id, StatusOrder status) {
        String url = ENDPOINT_BASE_ORDER + "/" + id + "/status";

        response = given().contentType(MediaType.APPLICATION_JSON_VALUE).headers(
                        "Authorization",
                        token,
                        "Accept",
                        ContentType.JSON)
                .params("newStatus", status).when().put(url);
    }

    @Dado("que existe um pedido na base de dados com status WAITINGPAYMENT")
    public void queExisteUmPedidoNaBaseDeDadosComStatusWAITINGPAYMENT() {
        createOrder();
    }

    @Quando("o cliente consultar os pedidos pelo status WAITINGPAYMENT")
    public void oClienteConsultarOsPedidosPeloStatusWAITINGPAYMENT() {
        response = given().get(ENDPOINT_BASE_ORDER + "/status/" + StatusOrder.WAITINGPAYMENT);
    }

    @Entao("o sistema deve retornar os pedidos com status WAITINGPAYMENT")
    public void oSistemaDeveRetornarOsPedidosComStatusWAITINGPAYMENT() {
        List<OrderResponse> ordersList = Arrays.stream(response.then().extract().as(OrderResponse[].class)).toList();
        Assert.assertNotNull(ordersList);
        Assert.assertFalse(ordersList.isEmpty());
        Assert.assertTrue(ordersList.stream().allMatch(order -> order.getStatus().equals(StatusOrder.WAITINGPAYMENT.toString())));
    }


    @Dado("que existe um pedido na base de dados")
    public void queExisteUmPedidoNaBaseDeDados() {
        createOrder();
    }

    @Quando("o cliente consultar os pedidos pelo Id")
    public void oClienteConsultarOsPedidosPeloId() {
        orderResponse = response.then().extract().as(OrderResponse.class);
        response = given().get(ENDPOINT_BASE_ORDER + "/" + orderResponse.getId());
    }

    @Entao("o sistema deve retornar o pedido")
    public void oSistemaDeveRetornarOPedido() {
        orderResponse = response.then().extract().as(OrderResponse.class);
        Assert.assertNotNull(orderResponse);
    }

    @Dado("que existe um pedido na base de dados com status APPROVEDPAYMENT")
    public void queExisteUmPedidoNaBaseDeDadosComStatusAPPROVEDPAYMENT() {
        createOrder();
        orderResponse = response.then().extract().as(OrderResponse.class);
        updateStatusOrder(orderResponse.getId(),StatusOrder.APPROVEDPAYMENT);
    }

    @Quando("o cliente passar o id e o status RECEIVED do pedido para a API de update status")
    public void oClientePassarOIdEOStatusRECEIVEDDoPedidoParaAAPIDeUpdateStatus() {
        orderResponse = response.then().extract().as(OrderResponse.class);
        updateStatusOrder(orderResponse.getId(),StatusOrder.RECEIVED);
    }

    @Entao("o sistema deve realizar o update")
    public void oSistemaDeveRealizarOUpdate() {
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK.value());
    }

    @E("o sistema deve retornar o pedido com o status trocado")
    public void oSistemaDeveRetornarOPedidoComOStatusTrocado() {
        orderResponse = response.then().extract().as(OrderResponse.class);
        Assert.assertTrue(orderResponse.getStatus().equals(StatusOrder.RECEIVED.toString()));
    }

    @Dado("que existe um pedido na base de dados com status REJECTEDPAYMENT")
    public void queExisteUmPedidoNaBaseDeDadosComStatusREJECTEDPAYMENT() {
        createOrder();
        orderResponse = response.then().extract().as(OrderResponse.class);
        updateStatusOrder(orderResponse.getId(),StatusOrder.REJECTEDPAYMENT);
    }

    @Entao("o sistema não deve realizar o update")
    public void oSistemaNãoDeveRealizarOUpdate() {
        Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST.value());
    }

    @E("o sistema deve retornar uma mensagem de erro")
    public void oSistemaDeveRetornarUmaMensagemDeErro() {
        ErrorResponse errorResponse = response.then().extract().as(ErrorResponse.class);
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), errorResponse.getStatus());
    }


}
