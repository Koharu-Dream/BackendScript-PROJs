package com.lab.jpa.sisbiblioteca.config;

import com.lab.jpa.sisbiblioteca.exception.AutorNaoEncontradoException;
import com.lab.jpa.sisbiblioteca.exception.DadosInvalidosException;
import com.lab.jpa.sisbiblioteca.model.Autor;
import com.lab.jpa.sisbiblioteca.service.AutorService;
import com.lab.jpa.sisbiblioteca.service.LivroService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AutorService autorService;
    private final LivroService livroService;

    public DataInitializer(AutorService autorService, LivroService livroService) {
        this.autorService = autorService;
        this.livroService = livroService;
    }

    @Override
    public void run(String... args) throws Exception {
        var scanner = new Scanner(System.in);
        var continuar = true;

        System.out.println("==========================================");
        System.out.println(" SISTEMA DE GESTÃO DE BIBLIOTECA JPA ");
        System.out.println("==========================================");

        while (continuar) {
            System.out.println("\nMENU DE OPÇÕES:");
            System.out.println("1 - Cadastrar Autor");
            System.out.println("2 - Listar Autores");
            System.out.println("3 - Cadastrar Livro");
            System.out.println("4 - Listar Livros");
            System.out.println("5 - Ver livros de um autor específico");
            System.out.println("6 - Atualizar nome de um Autor");
            System.out.println("7 - Remover Autor (e seus livros)");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            var opcao = scanner.nextLine();

            continuar = switch (opcao) {
                case "1" -> { cadastrarAutor(scanner); yield true; }
                case "2" -> { listarAutores(); yield true; }
                case "3" -> { cadastrarLivro(scanner); yield true; }
                case "4" -> { listarLivros(); yield true; }
                case "5" -> { verLivrosDeUmAutor(scanner); yield true; }
                case "6" -> { atualizarAutor(scanner); yield true; }
                case "7" -> { removerAutor(scanner); yield true; }
                case "0" -> { System.out.println("Encerrando aplicação..."); yield false; }
                default -> { System.out.println("Opção inválida! Tente novamente."); yield true; }
            };
        }
        System.out.println("Aplicação finalizada.");
    }

    private void cadastrarAutor(Scanner scanner) {
        System.out.print("Digite o nome do autor: ");
        var nome = scanner.nextLine();
        try {
            var autor = autorService.cadastrar(nome);
            System.out.println(">>> Autor '" + autor.getNome() + "' cadastrado com ID: " + autor.getId());
        } catch (DadosInvalidosException e) {
            System.out.println("Dados invalidos: " + e.getMessage());
        }
    }

    private void listarAutores() {
        var autores = autorService.listarTodos();
        if (autores.isEmpty()) {
            System.out.println("Nenhum autor cadastrado.");
            return;
        }
        System.out.println("\n--- LISTA DE AUTORES ---");
        autores.forEach(a -> System.out.printf("ID: %d | Nome: %s%n", a.getId(), a.getNome()));
        System.out.println("------------------------");
    }

    private void cadastrarLivro(Scanner scanner) {
        listarAutores();
        System.out.print("Informe o ID do autor do livro: ");
        var idStr = scanner.nextLine();
        try {
            var autorId = Long.parseLong(idStr);
            Autor autor = autorService.buscarPorId(autorId);

            System.out.print("Digite o título do livro: ");
            var titulo = scanner.nextLine();
            System.out.print("Digite o ano de publicação: ");
            var ano = Integer.parseInt(scanner.nextLine());

            var livro = livroService.cadastrar(titulo, ano, autor);
            System.out.println(">>> Livro '" + livro.getTitulo() + "' cadastrado com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("Valor numérico inválido informado.");
        } catch (AutorNaoEncontradoException | DadosInvalidosException e) {
            System.out.println(e.getMessage());
        }
    }

    private void listarLivros() {
        var livros = livroService.listarTodos();
        if (livros.isEmpty()) {
            System.out.println("Nenhum livro cadastrado.");
            return;
        }
        System.out.println("\n--- LISTA DE LIVROS ---");
        livros.forEach(l -> System.out.printf("ID: %d | Título: %s | Ano: %d | Autor: %s%n",
                l.getId(), l.getTitulo(), l.getAnoPublicacao(), l.getAutor().getNome()));
        System.out.println("-----------------------");
    }

    private void verLivrosDeUmAutor(Scanner scanner) {
        listarAutores();
        System.out.print("Informe o ID do autor para ver os livros: ");
        var idStr = scanner.nextLine();
        try {
            var autorId = Long.parseLong(idStr);
            var autor = autorService.buscarComLivros(autorId);
            System.out.println("\n--- LIVROS DE " + autor.getNome().toUpperCase() + " ---");
            if (autor.getLivros().isEmpty()) {
                System.out.println("Esse autor ainda não tem livros cadastrados.");
            } else {
                autor.getLivros().forEach(l -> System.out.printf("ID: %d | Título: %s | Ano: %d%n",
                        l.getId(), l.getTitulo(), l.getAnoPublicacao()));
            }
            System.out.println("----------------------------------");
        } catch (NumberFormatException e) {
            System.out.println("Valor numérico inválido informado.");
        } catch (AutorNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    private void atualizarAutor(Scanner scanner) {
        listarAutores();
        System.out.print("Informe o ID do autor que deseja atualizar: ");
        var idStr = scanner.nextLine();
        try {
            var autorId = Long.parseLong(idStr);
            System.out.print("Novo nome: ");
            var novoNome = scanner.nextLine();
            var autor = autorService.atualizarNome(autorId, novoNome);
            System.out.println(">>> Autor atualizado para '" + autor.getNome() + "'.");
        } catch (NumberFormatException e) {
            System.out.println("Valor numérico inválido informado.");
        } catch (AutorNaoEncontradoException | DadosInvalidosException e) {
            System.out.println(e.getMessage());
        }
    }

    private void removerAutor(Scanner scanner) {
        listarAutores();
        System.out.print("Informe o ID do autor que deseja remover: ");
        var idStr = scanner.nextLine();
        try {
            var autorId = Long.parseLong(idStr);
            autorService.remover(autorId);
            System.out.println(">>> Autor removido com sucesso (junto com os livros vinculados a ele).");
        } catch (NumberFormatException e) {
            System.out.println("Valor numérico inválido informado.");
        } catch (AutorNaoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }
}