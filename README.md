# Fiap 51 Burguer - Criação de pedido e produtos

Sistema de pedidos de lanche. Quarto tech challenge do curso de Pós Tech - turma 6SOAT de Software Architecture para aplicar conceitos Microserviço.
Microserviço de sistema de Criação de pedido e produtos

## 👨‍🔧👩‍🔧 Integrantes
Carlos Jafet - RM 354076 - cjafet07@gmail.com

Guilherme Macedo Moreira - RM 353750 - guilherme.macedomoreira@gmail.com

Isabella Bellinazzi da Silva - RM 354143 - isabellinazzi@hotmail.com

Juliano Silva Nunes - RM 354144 - silva.juliano8130@gmail.com

Thiago Augusto Nery - RM 355063 - doomerbr@gmail.com


# FIAP 51Burger - Produto e Pedido

## 🛠 Cobertura de testes e coverage do microserviço de Produto e Pedido

Testes unitários
![image](https://github.com/user-attachments/assets/24624475-3d4b-4495-ab56-c0570bbe0e5f)
![image](https://github.com/user-attachments/assets/967a443b-e724-4f12-88fc-6114374ed801)

Cucumber
![image](https://github.com/user-attachments/assets/479483e3-5962-459b-8e80-1df25bdad8af)


## 📁 Acesso ao Projeto

### Repositórios no GitHub

- **Infraestrutura SQL (postgres) Kubernetes com Terraform:**  
  [fiap-k8s51burguer](https://github.com/GuiMM/fiap-k8s51burguer)

- **Infraestrutura de Banco de Dados SQL (postgres) Gerenciáveis com Terraform:**  
  [fiap-db51burguer](https://github.com/GuiMM/fiap-db51burguer)

- **Infraestrutura de Banco de Dados NoSQL (mongodb) Gerenciáveis com Terraform:**  
  [fiap-atlasdb51burguer](https://github.com/GuiMM/fiap-atlasdb51burguer)
  
- **Link dos repositório dos microserviços aplicação que é executada no Kubernetes:**  
  - [Pedido e produto (Postgres-SQL)](https://github.com/Isa-Bellinazzi/fiap-product-and-order51burguer)
  - [Cliente (Postgres-SQL)](https://github.com/Tnery81/fiap-client51burger)
  - [Checkout (Mondo - NoSQL)](https://github.com/julianoBeerg/fiap-payment51burguer)

- **Funções Lambda:**  
  [fiap-lambda51burguer](https://github.com/julianoBeerg/fiap-lambda51burguer)

### Recursos Adicionais
- **Collection para Importar no Postman:**  
  [FIAP - Burger API.postman_collection.json](https://drive.google.com/file/d/1AbBfCI1HJ-XFCAL2n0L0opocKNKtDWep/view?usp=sharing)

- **Vídeo da Segunda Fase do Projeto:**  
  [YouTube - PosTech Software Architecture Grupo 51](https://www.youtube.com/watch?v=jiOKUzZcc_Y&ab_channel=PosTech-SoftwareArchitectureGrupo51)

- **Vídeo da Terceira Fase do Projeto:**  
  [YouTube - PosTech Software Architecture Grupo 51](https://www.youtube.com/watch?v=Zi2BFEvv9kk&ab_channel=PosTech-SoftwareArchitectureGrupo51)

- **Vídeo da quarta Fase do Projeto:**  
  [YouTube - PosTech Software Architecture Grupo 51](https://www.youtube.com/watch?v=Zi2BFEvv9kk&ab_channel=PosTech-SoftwareArchitectureGrupo51)

- **PDF com Vídeo, Diagramas e Modelagens:**  
  [Documento Google](https://docs.google.com/document/d/1Ay-OWOHbjec_wPjQI0ntPJny1N1lfZJFQqEHw97hONQ/edit?usp=drive_link)

- **PDF com Repositórios e Collection:**  
  [Documento Google](https://docs.google.com/document/d/1B933OMeg6z2DDZ-wWG-_dW9d0Q6TT2UhvOLmSbwQLnw/edit)


## 🔨 Funcionalidades do projeto de Criação de pedido e produtos

    - CRUD produtos.
                      
    - Buscar produtos por categoria.
                       
    - CRUD pedidos.
                     
    - Listar os pedidos.

    - Checkout do pedido que deve receber os produtos solicitados e retornar a identificação do pedido.

    - Webhook recebendo confirmação de pagamento.


## ✔️ Técnicas e tecnologias utilizadas

- `Aplicação`: Java 22
- `Testes`: junit5 e Cucumber
- `Banco de dados`: Postgres
- `Arquitetura`: Clean Architecture
- `Containerização`: Docker
- `Orquestação`: Kubernets
- `Design de software`: DDD
- `Nuvem`: AWS


## 📐 Diagramas e documentações

- [**Diagramas de Sequência:**](https://github.com/GuiMM/fiap-51burguer/blob/master/Diagrama%20de%20sequencia.png)

- [**Diagrama da arquitetura de autenticação**](https://drive.google.com/file/d/1mVJoEI81gEIqISXHRPgA1j_1fxkFm1ty/view)

- [**Diagrama da arquitetura do Sistema**](https://drive.google.com/file/d/1aOrEh3XR1r3pmZeIr5l475Kbfka61EgC/view?usp=sharing)

- [**MER - Modelo de entidade relacionamento**](https://drive.google.com/file/d/1-e6vfEpsNNS0aMtH_256b9I7Zsa-4o-2/view)

- [**Documentação da Modelagem de dados**](https://drive.google.com/file/d/1xu699uPLNKy73oYwtUhJYNGyGYmoQQeZ/view?usp=sharing)

## 🛠️ Abrir e rodar o projeto

**Instruções necessárias para abrir e executar o projeto**

Deverá ter instalado:

    - JDK 22
    - Docker(Certifique-se de que as opções de kubernets estejam habilitadas no docker desktop)

Após a instalação executar os comandos ordenados abaixo na pasta raiz da aplicação:

1 . **Aplicar ConfigMap:**

    - kubectl apply -f .\k8s\configmap-burguer-app.yaml


2 . **Aplicar Métricas:**

    - kubectl apply -f .\k8s\metrics.yaml

3 . **Aplicar Deployment do Banco de Dados:**

     - kubectl apply -f .\k8s\deployment-db.yaml

4 . **Aplicar Service do Banco de Dados:**

     - kubectl apply -f .\k8s\service-db.yaml

5 . **Aplicar Deployment da Aplicação:**

     - kubectl apply -f .\k8s\deployment-burguer-app.yaml

6 . **Aplicar Service da Aplicação:**

     - kubectl apply -f .\k8s\service-burguer-app.yaml

7 . **Aplicar Horizontal Pod Autoscaler:**

     - kubectl apply -f .\k8s\hpa-burguer-app.yaml


## 📚 Mais informações do curso
**Pós Tech - Turma 6SOAT**

**Tech challenge 2: Refatoração do projeto seguindo os padrões de clean code e clean architecture e orquestração de containers de forma escalável**

**Tech challenge 3: Refatoração da arquitetura para provisionar o cluster em cloud(AWS), banco no RDS e sistema de autenticação com Serveless lambda e cognito.**

**Tech challenge 4: Refatoração da arquitetura para provisionar Microserviços.**


## 📄 Licença
Não se aplica.
