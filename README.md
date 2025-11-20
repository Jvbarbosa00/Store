# 🛒 Store Backend API

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)

API RESTful desenvolvida para o gerenciamento de um sistema de E-commerce. O projeto foca na integridade de dados, relacionamentos complexos entre entidades e tratamento de erros robusto.

## 🚀 Tecnologias Utilizadas

* **Java 17**
* **Spring Boot 3** (Web, Data JPA)
* **MySQL 8** (Banco de Dados Relacional)
* **Lombok** (Redução de boilerplate code)
* **Maven** (Gerenciamento de dependências)

## ⚙️ Funcionalidades

* **Gerenciamento de Usuários e Produtos:** CRUD completo (Create, Read, Update, Delete).
* **Sistema de Pedidos:**
    * Criação de pedidos vinculados a usuários existentes.
    * Adição de múltiplos itens ao pedido.
    * **Segurança de Preço:** O sistema busca o preço real do produto no banco de dados no momento da compra, ignorando valores enviados pelo cliente para evitar fraudes.
    * Registro automático do momento da compra (`Instant`).
* **Tratamento Global de Erros:**
    * Respostas JSON padronizadas para erros 404 (Resource Not Found) e 409 (Database/Conflict).
* **Modelagem de Dados:**
    * Relacionamento **Um-para-Muitos** (User -> Orders).
    * Relacionamento **Muitos-para-Muitos** com atributos extras (Order -> OrderItem -> Product), utilizando chave composta (`@EmbeddedId`).
    * Uso de **Enums** para status do pedido (Aguardando Pagamento, Pago, Enviado, etc.).

## 🗄️ Modelo de Dados (ERD)

O banco de dados `store_db` é composto pelas seguintes tabelas principais:
* `tb_users`: Clientes da loja.
* `tb_products`: Catálogo de produtos.
* `tb_orders`: Cabeçalho dos pedidos.
* `tb_order_item`: Tabela associativa que armazena os itens, quantidade e preço histórico.

## 🛠️ Como Executar o Projeto

### Pré-requisitos
* Java JDK 17+ instalado.
* MySQL Server rodando.
* Maven instalado (ou usar o wrapper `./mvnw`).

### Passo a Passo

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/seu-usuario/nome-do-repo.git](https://github.com/seu-usuario/nome-do-repo.git)
   ```
2. **Configure o Banco de Dados:**
   Crie um banco de dados no MySQL:
   ```sql
   CREATE DATABASE store_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```
3. **Configure as Variáveis de Ambiente:**
   No seu IDE ou sistema operacional, defina as variáveis para não expor suas senhas:
   * `MYSQL_USERNAME`: seu_usuario (ex: root)
   * `MYSQL_PASSWORD`: sua_senha

4. **Execute a aplicação:**
   Utilize o Maven Wrapper para rodar o projeto sem precisar instalar o Maven manualmente:
   ```bash
   ./mvnw spring-boot:run
   ```
   ## 🔌 Endpoints da API

A API roda em `http://localhost:8080/api`.

### Produtos
* `GET /products` - Lista todos os produtos.
* `GET /products/{id}` - Busca um produto por ID.
* `POST /products` - Cria um novo produto.

### Usuários
* `GET /users` - Lista todos os usuários.
* `POST /users` - Cria um novo usuário.

### Pedidos (Orders)
* `GET /orders/{id}` - Busca um pedido e seus itens.
* `POST /orders` - Cria um pedido.

#### Exemplo de JSON para criar Pedido:
```json
{
    "orderStatus": 1,
    "client": {
        "id": 1
    },
    "items": [
        {
            "quantity": 2,
            "product": {
                "id": 1
            }
        },
        {
            "quantity": 1,
            "product": {
                "id": 2
            }
        }
    ]
}
