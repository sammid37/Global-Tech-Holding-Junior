# Desafio Técnico Global Tech Holding

Projeto avaliativo do processo seletivo da Global Tech Holding para a vaga de desenvolvedor Full Stack Júnior. O propósito do projeto é gerenciar o cadastro de pessoas e calcular o seu peso ideal, desenvolvido com Spring Boot, Angular e banco de dados PostgreSQL.

## Pré-requisitos

- Java 17+
- Maven
- Node.js e npm
- Angular CLI
- PostgreSQL

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

## Testes

### Executar os testes unitários

Na raiz do projeto, execute:

```bash
./mvnw test
```

### Relatório de cobertura de código (JaCoCo)

O projeto utiliza o [JaCoCo](https://www.jacoco.org/) para medir a cobertura dos testes. O relatório é gerado automaticamente ao rodar `./mvnw test`.

Após a execução, abra o relatório HTML no navegador:

```plain text
target/site/jacoco/index.html
```
> [**`http://127.0.0.1:5500/target/site/jacoco/index.html`**](http://127.0.0.1:5500/target/site/jacoco/index.html)

> O relatório exibe a cobertura por pacote, classe e método, destacando as linhas cobertas (verde) e não cobertas (vermelho).
