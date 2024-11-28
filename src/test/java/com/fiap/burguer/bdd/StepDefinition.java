package com.fiap.burguer.bdd;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

public class StepDefinition {
    @Dado("que o usuário preencha os itens do pedido")
    public void queOUsuárioPreenchaOsItensDoPedido() {
    }

    @Quando("o usuário submeter o pedido")
    public void oUsuárioSubmeterOPedido() {
    }

    @Entao("o sistema deve salvar o pedido")
    public void oSistemaDeveSalvarOPedido() {
    }

    @E("o sistema deve retornar os dados do pedido")
    public void oSistemaDeveRetornarOsDadosDoPedido() {
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
