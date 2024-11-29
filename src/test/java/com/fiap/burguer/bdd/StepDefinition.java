package com.fiap.burguer.bdd;

import com.fiap.burguer.core.application.enums.CategoryProduct;
import com.fiap.burguer.core.domain.Product;
import com.fiap.burguer.driver.dto.OrderItemRequest;
import com.fiap.burguer.driver.dto.OrderRequest;
import com.fiap.burguer.driver.dto.OrderResponse;
import com.fiap.burguer.driver.dto.ProductCreate;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class StepDefinition {

    private Response response;

    private OrderResponse orderResponse;

    private Product productResponse;

    private String ENDPOINT_BASE_ORDER = "http://localhost:8080/orders";

    private String ENDPOINT_BASE_PRODUCT = "http://localhost:8080/products";

    @Dado("que o usuário preencha os itens do pedido")
    public void queOUsuárioPreenchaOsItensDoPedido() {
        ProductCreate product = new ProductCreate("water","http://gangstaburguer.com/images/water.jpg", 2, "Agua cara", 15.0, CategoryProduct.DRINK);

        productResponse = given().contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(product).when().post(ENDPOINT_BASE_PRODUCT).then().extract().as(Product.class);

    }

    @Quando("o usuário submeter o pedido para criacao")
    public void oUsuárioSubmeterOPedido() {
        OrderRequest orderRequest = new OrderRequest();
        OrderItemRequest orderItemRequest = new OrderItemRequest();
        orderItemRequest.setProductId(1);
        orderItemRequest.setQuantity(2);
        orderRequest.setItems(List.of(orderItemRequest));
        orderResponse = given().contentType(MediaType.APPLICATION_JSON_VALUE).body(orderRequest).when().post(ENDPOINT_BASE_ORDER).then().extract().as(OrderResponse.class);
    }

    @Entao("o sistema deve salvar o pedido")
    public void oSistemaDeveSalvarOPedido() {
        response.then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @E("o sistema deve retornar os dados do pedido")
    public void oSistemaDeveRetornarOsDadosDoPedido() {
        response.then().assertThat().body(matchesJsonSchemaInClasspath("orderResponse.json"));
    }

    @Dado("que o usuário está na página de login")
    public void queOUsuárioEstáNaPáginaDeLogin() {
    }

    @Quando("o usuário preencher os dados do pedido")
    public void oUsuárioPreencherOsDadosDoPedido() {
    }

    @Entao("o usuário deve ser redirecionado para a página de sucesso")
    public void oUsuárioDeveSerRedirecionadoParaAPáginaDeSucesso() {
    }

    @Dado("que existe um pedido na base de dados")
    public void queExisteUmPedidoNaBaseDeDados() {
    }

    @Quando("o cliente consultar os pedidos")
    public void oClienteConsultarOsPedidos() {
    }

    @Entao("o sistema deve retornar o pedido")
    public void oSistemaDeveRetornarOPedido() {
    }

    @Dado("que existe um pedido na base de dados com status WAITINGPAYMENT")
    public void queExisteUmPedidoNaBaseDeDadosComStatusWAITINGPAYMENT() {
    }

    @Quando("o cliente consultar os pedidos pelo status WAITINGPAYMENT")
    public void oClienteConsultarOsPedidosPeloStatusWAITINGPAYMENT() {
    }

    @Quando("o cliente consultar os pedidos pelo Id")
    public void oClienteConsultarOsPedidosPeloId() {
    }

    @Dado("que existe um pedido na base de dados com status APPROVEDPAYMENT")
    public void queExisteUmPedidoNaBaseDeDadosComStatusAPPROVEDPAYMENT() {
    }

    @Quando("o cliente passar o id e o status RECEIVED do pedido para a API de udate status")
    public void oClientePassarOIdEOStatusRECEIVEDDoPedidoParaAAPIDeUdateStatus() {
    }

    @Entao("o sistema deve realizar o update")
    public void oSistemaDeveRealizarOUpdate() {
    }

    @E("o sistema deve retornar o pedido com o status trocado")
    public void oSistemaDeveRetornarOPedidoComOStatusTrocado() {
    }

    @Dado("que existe um pedido na base de dados com status REJECTEDPAYMENT")
    public void queExisteUmPedidoNaBaseDeDadosComStatusREJECTEDPAYMENT() {
    }

    @Entao("o sistema não deve realizar o update")
    public void oSistemaNãoDeveRealizarOUpdate() {
    }

    @E("o sistema deve retornar uma mensagem de erro")
    public void oSistemaDeveRetornarUmaMensagemDeErro() {
    }

    @Quando("o cliente passar o id e o status CANCELED do pedido para a API de udate status")
    public void oClientePassarOIdEOStatusCANCELEDDoPedidoParaAAPIDeUdateStatus() {
    }
}
