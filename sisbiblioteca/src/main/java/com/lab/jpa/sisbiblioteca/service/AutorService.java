package com.lab.jpa.sisbiblioteca.service;

import com.lab.jpa.sisbiblioteca.exception.AutorNaoEncontradoException;
import com.lab.jpa.sisbiblioteca.exception.DadosInvalidosException;
import com.lab.jpa.sisbiblioteca.model.Autor;
import com.lab.jpa.sisbiblioteca.repository.AutorRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AutorService {

    private final AutorRepository autorRepository;
    private final Validator validator;

    public AutorService(AutorRepository autorRepository, Validator validator) {
        this.autorRepository = autorRepository;
        this.validator = validator;
    }

    public Autor cadastrar(String nome) {
        var autor = new Autor(nome);
        validar(autor);
        return autorRepository.save(autor);
    }

    public List<Autor> listarTodos() {
        return autorRepository.findAll();
    }

    // busca simples, usada quando nao precisa da lista de livros
    public Autor buscarPorId(Long id) {
        return autorRepository.findById(id)
                .orElseThrow(() -> new AutorNaoEncontradoException(id));
    }

    // busca com JOIN FETCH, usada quando precisa acessar autor.getLivros()
    public Autor buscarComLivros(Long id) {
        return autorRepository.buscarComLivros(id)
                .orElseThrow(() -> new AutorNaoEncontradoException(id));
    }

    public Autor atualizarNome(Long id, String novoNome) {
        var autor = buscarPorId(id);
        autor.setNome(novoNome);
        validar(autor);
        return autorRepository.save(autor);
    }

    public void remover(Long id) {
        if (!autorRepository.existsById(id)) {
            throw new AutorNaoEncontradoException(id);
        }
        autorRepository.deleteById(id);
    }

    // roda as regras do Bean Validation (@NotBlank etc) antes de salvar
    private void validar(Autor autor) {
        Set<ConstraintViolation<Autor>> violacoes = validator.validate(autor);
        if (!violacoes.isEmpty()) {
            var mensagem = violacoes.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("; "));
            throw new DadosInvalidosException(mensagem);
        }
    }
}