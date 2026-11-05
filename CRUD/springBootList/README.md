# springBootList

CRUD de produtos com Spring Boot, Spring Data JPA, DTOs (Records), Thymeleaf e MySQL.
Baseado no tutorial do Prof. Me. Marcos Moraes (Professor Maromo) - Fatec Mogi Mirim.

## Pré-requisitos
- JDK 17 ou superior
- MySQL Server em execução (porta 3306)
- Maven 3.9+ instalado e no PATH

## Banco de dados
Execute `sql/script.sql` no MySQL Workbench (ou deixe o Hibernate criar a tabela sozinho:
a URL já usa `createDatabaseIfNotExist=true`).

## Configuração
Ajuste usuário e senha em `src/main/resources/application.properties`
(padrão: usuário `root`, senha `1234`), ou defina as variáveis de ambiente
`SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME` e `SPRING_DATASOURCE_PASSWORD`.

## Executar
```
mvn spring-boot:run
```
(Opcional: gere o wrapper com `mvn wrapper:wrapper` e depois use `./mvnw spring-boot:run`
ou `mvnw.cmd spring-boot:run` no Windows.)
Acesse: http://localhost:8080/products
