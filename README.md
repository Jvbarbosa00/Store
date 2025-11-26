![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)

API RESTful desenvolvida para o gerenciamento de um sistema de E-commerce. O projeto foca na integridade de dados, segurança, regras de negócio reais (estoque, preços), relacionamentos complexos e documentação automática.

## 🚀 Tecnologias Utilizadas

* **Java 17+**
* **Spring Boot 3** (Web, Data JPA, Security)
* **MySQL 8** (Banco de Dados Relacional)
* **SpringDoc / Swagger** (Documentação da API)
* **JWT (JSON Web Token)** (Autenticação e Autorização)
* **JUnit 5 & Mockito** (Testes Unitários)
* **Docker & Docker Compose** (Containerização)
* **Lombok** (Redução de boilerplate code)
* **Maven** (Gerenciamento de dependências)

## ⚙️ Funcionalidades

* **Gerenciamento de Entidades:** CRUD para Usuários, Produtos e Categorias.
* **Autenticação e Segurança:**
    * Sistema de Login e Registro com **JWT**.
    * Proteção de rotas (apenas usuários autenticados podem realizar pedidos).
    * Criptografia de senhas com **BCrypt**.
* **Categorização:** Produtos organizados por categorias (Relacionamento Muitos-para-Muitos).
* **Sistema de Pedidos:**
    * Criação de pedidos vinculados a usuários existentes.
    * **Baixa de Estoque:** Validação automática de disponibilidade e subtração do estoque ao confirmar o pedido.
    * **Segurança de Preço:** O sistema utiliza o preço histórico do produto no momento da compra, prevenindo fraudes via JSON.
    * **Cálculos Automáticos:** A API retorna campos calculados de `subTotal` (por item) e `total` (valor final do pedido).
* **Qualidade de Código:**
    * **Testes Unitários:** Cobertura de testes para regras de negócio críticas (ex: validação de estoque) utilizando JUnit e Mockito.
* **Tratamento Global de Erros:**
    * Respostas JSON padronizadas para erros 400 (Bad Request - Estoque insuficiente), 401 (Unauthorized), 403 (Forbidden), 404 (Not Found) e 409 (Conflict).
* **Database Seeding:**
    * População automática do banco de dados com usuários, produtos, categorias e pedidos de teste ao iniciar a aplicação.
* **Documentação:**
    * Interface Swagger UI interativa para testes e visualização de endpoints.

## 🗄️ Modelo de Dados

O banco de dados `store_db` é composto pelas seguintes tabelas principais:
* `tb_users`: Clientes da loja (com credenciais seguras).
* `tb_categories`: Categorias dos produtos.
* `tb_products`: Catálogo de produtos.
* `tb_product_category`: Tabela de união (Produto <-> Categoria).
* `tb_orders`: Cabeçalho dos pedidos.
* `tb_order_item`: Tabela associativa com itens, quantidade, preço histórico e subtotais.

## 🛠️ Como Executar o Projeto

Você pode rodar o projeto de duas formas: **Via Docker (Recomendado)** ou **Localmente**.

### Opção 1: Via Docker (Rápido e Fácil) 🐳
Não é necessário instalar Java ou MySQL na sua máquina, apenas o Docker.

1. **Clone o repositório:**
   ```bash
   git clone [https://github.com/Jvbarbosa00/Store.git](https://github.com/Jvbarbosa00/Store.git)
   cd Store
   ```
2. **Suba o ambiente:**
   ```bash
   docker compose up --build
   ```

### Opção 2: Execução Local(Tradicional)
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

   * `JWT_SECRET`:sua_chave_secreta_para_tokens


5. **Execute a aplicação:**

   Utilize o Maven Wrapper para rodar o projeto sem precisar instalar o Maven manualmente:

   ```bash

   ./mvnw spring-boot:run

   ```

   ## 🔌 Endpoints da API



A API roda em `http://localhost:8080/store`.


📘 Swagger UI (Documentação Interativa)

Acesse a documentação completa e teste as requisições diretamente pelo navegador: 👉 http://localhost:8080/store/swagger-ui/index.html



### Principais Endpoints

🔒 Nota: A maioria dos endpoints requer um Token JWT no Header Authorization: Bearer <token>. Obtenha o token através do endpoint de Login ou Registro.

#### Autenticação
* **POST** `/auth/register` - Cria um novo usuário e retorna o Token JWT.
* **POST** `/auth/login` - Autentica um usuário e retorna o Token JWT.


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

🧪 Testes

Para executar os testes unitários e garantir a integridade das regras de negócio (como validação de estoque):

   ```bash
   ./mvnw test
   ```

🤝 Contribuindo

Este projeto é para fins de estudo e portfólio. Sugestões e dicas são sempre bem-vindas!



Desenvolvido por João Victor Barbosa.
