package com.universidade.catalogo.domain.exception;
/**
* Exceção de domínio de tempo de execução (Unchecked Exception) indicando
* que uma entidade solicitada não existe na camada de persistência.
*/
public class RecursoNaoEncontradoException extends RuntimeException {
public RecursoNaoEncontradoException(String mensagem) {
super(mensagem);
}
}