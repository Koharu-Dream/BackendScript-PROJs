package com.lab.jpa.sisbiblioteca.exception;

// excecao personalizada, lancada quando os dados nao passam na validacao (Bean Validation)
public class DadosInvalidosException extends RuntimeException {

    public DadosInvalidosException(String mensagem) {
        super(mensagem);
    }
}