# language: pt

  Funcionalidade: Pedido

    Cenario: Criar Pedido
      Dado que o usuário preencha os itens do pedido
      Quando o usuário submeter o pedido para criacao
      Entao o sistema deve salvar o pedido
      E o sistema deve retornar os dados do pedido

    Cenario: Listar Pedidos
      Dado que existe um pedido na base de dados
      Quando o cliente consultar os pedidos
      Entao o sistema deve retornar o pedido

    Cenario: Listar pedido por status
      Dado que existe um pedido na base de dados com status WAITINGPAYMENT
      Quando o cliente consultar os pedidos pelo status WAITINGPAYMENT
      Entao o sistema deve retornar o pedido

    Cenario: Listar pedido por Id
      Dado que existe um pedido na base de dados
      Quando o cliente consultar os pedidos pelo Id
      Entao o sistema deve retornar o pedido

    Cenario: update de status do pedido de APPROVEDPAYMENT para RECEIVED
      Dado que existe um pedido na base de dados com status APPROVEDPAYMENT
      Quando o cliente passar o id e o status RECEIVED do pedido para a API de udate status
      Entao o sistema deve realizar o update
      E o sistema deve retornar o pedido com o status trocado

    Cenario: update de status do pedido de REJECTEDPAYMENT para RECEIVED
      Dado que existe um pedido na base de dados com status REJECTEDPAYMENT
      Quando o cliente passar o id e o status RECEIVED do pedido para a API de udate status
      Entao o sistema não deve realizar o update
      E o sistema deve retornar uma mensagem de erro


    Cenario: update de status do pedido de APPROVEDPAYMENT para CANCELED
      Dado que existe um pedido na base de dados com status APPROVEDPAYMENT
      Quando o cliente passar o id e o status CANCELED do pedido para a API de udate status
      Entao o sistema não deve realizar o update
      E o sistema deve retornar uma mensagem de erro