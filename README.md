![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)

API RESTful desenvolvida para o gerenciamento de um sistema de E-commerce. O projeto foca na integridade de dados, regras de negócio reais (estoque, preços), relacionamentos complexos e documentação automática.

## 🚀 Tecnologias Utilizadas

* **Java 17+**
* **Spring Boot 3** (Web, Data JPA)
* **MySQL 8** (Banco de Dados Relacional)
* **SpringDoc / Swagger** (Documentação da API)
* **Lombok** (Redução de boilerplate code)
* **Maven** (Gerenciamento de dependências)

## ⚙️ Funcionalidades

* **Gerenciamento de Entidades:** CRUD para Usuários, Produtos e Categorias.
* **Categorização:** Produtos organizados por categorias (Relacionamento Muitos-para-Muitos).
* **Sistema de Pedidos:**
    * Criação de pedidos vinculados a usuários existentes.
    * **Baixa de Estoque:** Validação automática de disponibilidade e subtração do estoque ao confirmar o pedido.
    * **Segurança de Preço:** O sistema utiliza o preço histórico do produto no momento da compra, prevenindo fraudes via JSON.
    * **Cálculos Automáticos:** A API retorna campos calculados de `subTotal` (por item) e `total` (valor final do pedido).
* **Tratamento Global de Erros:**
    * Respostas JSON padronizadas para erros 400 (Bad Request - Estoque insuficiente), 404 (Not Found) e 409 (Conflict).
* **Database Seeding:**
    * População automática do banco de dados com usuários, produtos, categorias e pedidos de teste ao iniciar a aplicação.
* **Documentação:**
    * Interface Swagger UI interativa para testes e visualização de endpoints.

## 🗄️ Modelo de Dados

O banco de dados `store_db` é composto pelas seguintes tabelas principais:
* `tb_users`: Clientes da loja.
* `tb_categories`: Categorias dos produtos.
* `tb_products`: Catálogo de produtos.
* `tb_product_category`: Tabela de união (Produto <-> Categoria).
* `tb_orders`: Cabeçalho dos pedidos.
* `tb_order_item`: Tabela associativa com itens, quantidade, preço histórico e subtotais.

## 🛠️ Como Executar o Projeto

### Pré-requisitos
* Java JDK 17+ instalado.
* MySQL Server rodando.
* Maven instalado (ou usar o wrapper `./mvnw`).

### Passo a Passo

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/Jvbarbosa00/Store.git](https://github.com/Jvbarbosa00/Store.git)
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

A API roda em `http://localhost:8080/store`.

📘 Swagger UI (Documentação Interativa)
Acesse a documentação completa e teste as requisições diretamente pelo navegador: 👉 http://localhost:8080/api/swagger-ui/index.html

### Principais Endpoints

#### Categorias
* **GET** `/categories` - Lista todas as categorias.
* **GET** `/categories/{id}` - Busca categoria por ID.

#### Produtos
* **GET** `/products` - Lista todos os produtos (com suas categorias).
* **GET** `/products/{id}` - Busca um produto por ID.
* **POST** `/products` - Cria um novo produto.

#### Usuários
* **GET** `/users` - Lista todos os usuários.
* **POST** `/users` - Cria um novo usuário.

#### Pedidos (Orders)
* **GET** `/orders/{id}` - Busca um pedido completo (com itens e totais).
* **POST** `/orders` - Cria um pedido (Valida estoque e preços).

##### Exemplo de JSON para criar Pedido:
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
```
🤝 Contribuindo
Este projeto é para fins de estudo e portfólio. Sugestões e dicas são sempre bem-vindas!

Desenvolvido por João Victor Barbosa.
