# Desafio Técnico Global Tech Holding

Projeto avaliativo do processo seletivo da Global Tech Holding para a vaga de desenvolvedor Full Stack Júnior. O propósito do projeto é gerenciar o cadastro de pessoas e calcular o seu peso ideal, desenvolvido com Spring Boot, Angular e banco de dados PostgreSQL.

![preview image](/public/preview.png)

## Estrutura do Projeto

```
health-person/
├── public/                         
├── src/
│   ├── frontend/                   # Aplicação Angular (frontend)
│   │   └── src/
│   │       └── app/
│   │           ├── core/
│   │           │   └── services/   # Serviço HTTP para comunicação com a API
│   │           ├── pages/
│   │           │   └── pessoas/    # Componentes de listagem, cadastro e edição
│   │           └── shared/
│   │               ├── models/     # Interfaces TypeScript da entidade Pessoa
│   │               └── validators/ # Validadores de CPF e data de nascimento
│   ├── main/
│   │   ├── java/com/sammid37/health_person/
│   │   │   ├── controller/         # Controllers REST da API
│   │   │   ├── dto/                # Data Transfer Objects (DTOs)
│   │   │   ├── exception/          # Tratamento global de exceções
│   │   │   ├── mapper/             # Mapeamento entre entidade e DTO
│   │   │   ├── model/              # Entidade JPA (Pessoa) e enumerações
│   │   │   ├── repository/         # Interface de acesso ao banco de dados
│   │   │   ├── service/            # Lógica de negócio
│   │   │   ├── task/               # Tarefas agendadas
│   │   │   └── validation/         # Anotação e validador customizado de CPF
│   │   └── resources/
│   │       ├── application.properties 
│   │       └── database/
│   │           └── script.sql     # Script de criação do banco de dados
│   └── test/java/com/sammid37/health_person/
│       ├── service/               # Testes unitários da camada de serviço
│       └── validation/            # Testes de validação do DTO
├── pom.xml                        # Configuração do Maven e dependências do backend
└── mvnw / mvnw.cmd                
```

## Pré-requisitos

- Java 21
- Maven
- Node.js e npm
- Angular CLI
- PostgreSQL 18

## Instalação

1. Clone o repositório:
```bash
git clone https://github.com/sammid37/health-person.git
cd health-person
```

2. Configure o banco de dados no arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/health_person_db
spring.datasource.username=postgres
spring.datasource.password=1234
```

> Altere os valores de `username` e `password` conforme as credenciais do seu ambiente local. Certifique-se de que o banco de dados `health_person_db` foi criado no PostgreSQL antes de executar a aplicação.

> 📖 A documentação da API do backend pode ser vista em [**`http://localhost:8080/swagger-ui/index.html`**](http://localhost:8080/swagger-ui/index.html)

3. Instale as dependências do frontend:
```bash
cd src/frontend
npm install
```

## Execução

1. Execute o backend pela raiz do projeto com Maven:
```bash
./mvnw spring-boot:run
```
> Também é possível executar diretamente pela IDE (ex: IntelliJ IDEA) rodando a classe principal da aplicação.

2. Execute o frontend:
```bash
cd src/frontend
ng serve
```

3. Acesse a aplicação em `http://localhost:4200/`.

## 🧪 Testes

Sobre os testes implementados neste projeto e cobertura dos testes. 

### Executar os testes unitários do Backend

Na raiz do projeto, execute:

```bash
./mvnw test
```

O projeto utiliza o [JaCoCo](https://www.jacoco.org/) para medir a cobertura dos testes. O relatório é gerado automaticamente ao rodar `./mvnw test`.

Após a execução, abra o relatório HTML no navegador:

```plain text
target/site/jacoco/index.html
```
> [**`http://127.0.0.1:5500/target/site/jacoco/index.html`**](http://127.0.0.1:5500/target/site/jacoco/index.html)

> O relatório exibe a cobertura por pacote, classe e método, destacando as linhas cobertas (verde) e não cobertas (vermelho).

### Executar os testes unitários do Frontend

O projeto utiliza o [Vitest](https://vitest.dev/) como framework de testes unitários do frontend, integrado ao Angular CLI via `@angular/build:unit-test`.

Acesse o diretório do frontend e execute:

```bash
cd src/frontend
ng test
```

Para gerar o relatório de cobertura, execute:

```bash
ng test --coverage
```

Após a execução, o relatório será gerado na pasta:

```plain text
src/frontend/coverage/index.html
```

> O relatório exibe a cobertura por arquivo e função, destacando as linhas cobertas e não cobertas.
