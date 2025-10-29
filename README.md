# API de Gerenciamento de Estoque de Games (Arquitetura Hexagonal)
 
![Java](https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green?style=for-the-badge&logo=spring)
![Maven](https://img.shields.io/badge/Maven-4.0-red?style=for-the-badge&logo=apache-maven)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-85EA2D?style=for-the-badge&logo=swagger)
 
## 📜 Descrição
 
Este projeto é uma API RESTful para gerenciamento de um estoque de games, desenvolvida como um estudo aprofundado de arquitetura de software. Diferente de um CRUD tradicional em camadas, a aplicação foi construída utilizando **Arquitetura Hexagonal (Portas e Adaptadores)**, um padrão que visa isolar o núcleo de regras de negócio de dependências externas como frameworks, bancos de dados e APIs.
 
O resultado é um sistema robusto, flexível, altamente testável e fácil de manter, onde a lógica de negócio é a peça central e independente de tecnologia.
 
## ✨ Principais Funcionalidades
 
- **CRUD Completo:** Operações de Criar, Ler, Atualizar e Deletar para a entidade `Game`.
- **Controle de Estoque:** Endpoints dedicados para aumentar e diminuir a quantidade de um game no estoque.
- **Buscas Avançadas:** Pesquisa de games por título, gênero ou plataforma (case-insensitive).
- **Validação de Dados:** Validação robusta dos dados de entrada usando Jakarta Bean Validation.
- **Tratamento de Exceções Centralizado:** Um `GlobalExceptionHandler` que captura erros e retorna respostas HTTP padronizadas.
- **Documentação de API:** A API é totalmente documentada com Swagger/OpenAPI, facilitando o teste e a integração.
- **Padrões de Projeto Avançados:**
    - **Strategy Pattern:** Para encapsular as operações de `IncreaseStock` e `DecreaseStock`.
    - **Decorator Pattern:** Para adicionar funcionalidades transversais (logging e validação de ID) ao caso de uso principal sem modificar seu código.
- **Auditoria no Banco de Dados:** Utiliza uma Stored Function e um Trigger em MySQL para auditar automaticamente todas as alterações de estoque.
 
## 🛠️ Tecnologias Utilizadas
 
- **Backend:**
    - **Java 17:** Linguagem de programação principal.
    - **Spring Boot 3:** Framework principal para desenvolvimento web, injeção de dependências e configuração.
    - **Spring Data JPA:** Para persistência de dados de forma simplificada.
    - **Hibernate:** Implementação do JPA.
- **Banco de Dados:**
    - **MySQL 8:** Sistema de gerenciamento de banco de dados relacional.
- **Flyway:** Ferramenta para versionamento e migração de schema do banco de dados (embora desabilitado no `application.properties` para desenvolvimento, a estrutura está presente).
- **Build & Dependências:**
    - **Apache Maven:** Gerenciador de dependências e build do projeto.
    - **Lombok:** Para reduzir código boilerplate (getters, setters, construtores).
    - **MapStruct:** Para gerar mappers de forma eficiente e segura em tempo de compilação.
- **Testes:**
    - **JUnit 5:** Framework de testes unitários.
    - **Mockito:** Para criação de mocks e dublês de teste.
- **Documentação:**
    - **Springdoc OpenAPI (Swagger):** Para geração automática da documentação da API.
 
## 📁 Estrutura de Pastas
 
A estrutura do projeto reflete a Arquitetura Hexagonal, com uma separação clara entre o `core` (domínio) e a `infrastructure` (adaptadores).
 
```
.
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com/cdb/estoque
│ │ │ ├── Application.java        # Ponto de entrada do Spring Boot
│   │   │       ├── core
│   │   │       │   ├── domain              # O coração do negócio
│ │ │ │ │ ├── model # (Ex: Game.java - POJO puro)
│   │   │       │   │   ├── strategy        # (Padrão Strategy para estoque)
│   │   │       │   │   └── decorator       # (Padrão Decorator para logging)
│   │   │       │   ├── port
│   │   │       │   │   ├── input           # PORTAS DE ENTRADA (Contratos da API)
│   │   │       │   │   └── output          # PORTAS DE SAÍDA (Contratos de persistência)
│   │   │       │   └── useCase             # Lógica de negócio (Implementação das portas)
│   │   │       ├── adapter
│   │   │       │   ├── input               # ADAPTADORES DE ENTRADA
│   │   │       │   │   ├── controller      # (Ex: GameController - REST)
│   │   │       │   │   ├── mapper          # (MapStruct para DTOs)
│   │   │       │   │   ├── request         # (Objetos de entrada - GameRequest)
│   │   │       │   │   └── response        # (Objetos de saída - GameResponse)
│   │   │       │   └── output              # ADAPTADORES DE SAÍDA
│   │   │       │       ├── entity          # (Entidades JPA - GameEntity)
│   │   │       │       ├── mapper          # (MapStruct para Entidades)
│   │   │       │       └── persistence     # (Implementação da porta de persistência)
│   │   │       ├── exception               # Tratamento global de exceções
│   │   │       └── infrastructure          # Configurações do Spring (Beans, OpenAPI)
│   │   └── resources
│ │ ├── application.properties    # Configurações da aplicação
│   │       └── db/migration                # Scripts SQL para o Flyway
│   └── test                              # Testes unitários e de integração
...
```
 
## 🔌 Endpoints da API
 
Acesse a documentação interativa do Swagger em: `http://localhost:8080/swagger-ui.html`
 
| Método HTTP | URL                                      | Descrição                                    |
|-------------|------------------------------------------|------------------------------------------------|
| `GET`       | `/games`                                 | Lista todos os games.                          |
| `GET`       | `/games/{id}`                            | Busca um game por ID.                          |
| `POST`      | `/games`                                 | Cria um novo game.                             |
| `PUT`       | `/games/{id}`                            | Atualiza um game existente.                    |
| `DELETE`    | `/games/{id}`                            | Deleta um game.                                |
| `PATCH`     | `/games/{id}/increase-stock?quantity={q}`| Aumenta o estoque de um game.                  |
| `PATCH`     | `/games/{id}/decrease-stock?quantity={q}`| Diminui o estoque de um game.                  |
| `GET`       | `/games/search/title?titleGame={t}`      | Busca games por parte do título.               |
| `GET`       | `/games/search/genre?genre={g}`          | Busca games por parte do gênero.               |
| `GET`       | `/games/search/plataform?plataform={p}`  | Busca games por parte da plataforma.           |
 
## ⚙️ Requisitos e Configuração para Rodar
 
**Requisitos:**
- Java (JDK) 17 ou superior.
- Apache Maven 3.8+
- MySQL Server 8.0+
 
**Passos para Configuração:**
 
1.  **Clone o repositório:**

    ```
    git clone https://github.com/PedroHenri10/Arquitetura-Hexagonal-CRUD-Estoque-CDB
    cd Arquitetura-Hexagonal-CRUD-Estoque-CDB
    ```

2.  **Configure o Banco de Dados:**
    - Crie um banco de dados no seu MySQL chamado `gamestore`.
- Abra o arquivo `src/main/resources/application.properties`.
    - Altere as propriedades `spring.datasource.username` e `spring.datasource.password` com as suas credenciais do MySQL.
3.  **Habilite o Flyway (Opcional, mas recomendado):**
- No `application.properties`, altere a linha `spring.flyway.enabled=false` para `true`.
    - Mude `spring.jpa.hibernate.ddl-auto=update` para `validate`. Isso garante que o Flyway gerencia o schema, não o Hibernate.
4.  **Execute a aplicação:**
    - Pela linha de comando:
      ```bash
      mvn spring-boot:run
      ```
- Ou importe o projeto em sua IDE (IntelliJ, Eclipse, VSCode) e execute a classe `Application.java`.
 
A aplicação estará disponível em `http://localhost:8080`.
 
## 🚀 Melhorias Futuras
 
- [ ] **Implementar autenticação e autorização com JWT** para proteger os endpoints.
- [ ] Adicionar paginação nos endpoints de listagem (`GET /games`).
- [ ] Implementar um cache (ex: com Redis) para as consultas mais frequentes.
- [ ] Adicionar mais regras de negócio complexas (ex: promoções, descontos por plataforma).
- [ ] Containerizar a aplicação e o banco de dados com Docker e Docker Compose.
 
## 👨‍💻 Autor
**Pedro Henrique**
- Email: dinhonoliver@gmail.com

## 👨‍💻 Contribuidora
** Tarita De Lima Silva**
