package com.lab.jpa.sisbiblioteca.service;

import com.lab.jpa.sisbiblioteca.exception.DadosInvalidosException;
import com.lab.jpa.sisbiblioteca.model.Autor;
import com.lab.jpa.sisbiblioteca.model.Livro;
import com.lab.jpa.sisbiblioteca.repository.LivroRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final Validator validator;

    public LivroService(LivroRepository livroRepository, Validator validator) {
        this.livroRepository = livroRepository;
        this.validator = validator;
    }

    public Livro cadastrar(String titulo, Integer anoPublicacao, Autor autor) {
        var livro = new Livro(titulo, anoPublicacao, autor);
        validar(livro);
        return livroRepository.save(livro);
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    // roda as regras do Bean Validation (@NotBlank, @Positive etc) antes de salvar
    private void validar(Livro livro) {
        Set<ConstraintViolation<Livro>> violacoes = validator.validate(livro);
        if (!violacoes.isEmpty()) {
            var mensagem = violacoes.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("; "));
            throw new DadosInvalidosException(mensagem);
        }
    }
}