# Sisbiblioteca - Sistema de Gestão de Biblioteca

Atividade Prática P1, desenvolvida com Java 17+, Spring Boot, Spring Data JPA e banco H2 em memória, com menu interativo via console (CommandLineRunner).

## Autores

- Beatriz Mariana Antolini Carvalheiro, RA: 1630482511025
- Caio Vitor de Souza Torres Morais, RA: 1630482511012

## Tecnologias utilizadas

- Java 17+
- Spring Boot 4.1.1
- Spring Data JPA
- Banco de dados H2 (em memória)
- Bean Validation (Hibernate Validator)
- Lombok

## Estrutura do projeto

- `SisbibliotecaApplication.java`
- `config/` -> menu interativo no console
- `model/` -> entidades Autor e Livro
- `repository/` -> interfaces JpaRepository
- `service/` -> regras de negócio (Autor e Livro)
- `exception/` -> exceções personalizadas

## Como executar

Rode `mvnw spring-boot:run` no terminal, ou clique no botão verde ao lado da classe `SisbibliotecaApplication`.

O menu aparece direto no terminal. Para ver as tabelas no console web do H2:

- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:sisbibliotecadb`
- User: `sa`
- Password: (em branco)

## Modificações realizadas

Além do que foi pedido no roteiro base (cadastrar e listar Autor/Livro), foram implementadas 5 modificações:

1. **CRUD completo de Autor** - além de cadastrar e listar, agora é possível atualizar o nome de um autor e remover um autor (junto com os livros vinculados a ele).
2. **Consulta de livros por autor com JOIN FETCH** - método `buscarComLivros` no `AutorRepository`, evitando o erro de `LazyInitializationException` ao acessar a lista de livros de um autor fora de uma transação.
3. **Validação de dados (Bean Validation)** - as entidades `Autor` e `Livro` usam `@NotBlank`, `@NotNull` e `@Positive` para garantir que nome, título e ano de publicação sejam válidos antes de salvar no banco.
4. **Tratamento de exceções personalizado** - criadas as exceções `AutorNaoEncontradoException` e `DadosInvalidosException`, lançadas pelos services quando um autor não é encontrado ou os dados informados são inválidos.
5. **Camada de Service** - a lógica de negócio (antes dentro do `DataInitializer`) foi movida para `AutorService` e `LivroService`, deixando o `DataInitializer` responsável só pela interação com o usuário no console.