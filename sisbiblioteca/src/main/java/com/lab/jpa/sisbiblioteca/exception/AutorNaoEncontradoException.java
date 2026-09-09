package com.lab.jpa.sisbiblioteca.exception;

// exceção personalizada, lançada quando o autor nao existe no banco
public class AutorNaoEncontradoException extends RuntimeException {

    public AutorNaoEncontradoException(Long id) {
        super("Autor nao encontrado com o ID: " + id);
    }
}