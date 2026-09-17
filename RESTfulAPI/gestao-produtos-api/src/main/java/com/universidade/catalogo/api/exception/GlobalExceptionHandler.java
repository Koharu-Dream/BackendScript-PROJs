package com.universidade.catalogo.api.exception;
import com.universidade.catalogo.domain.exception.RecursoNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
/**
* Interceptor global de exceções para a camada REST.
* Centraliza o tratamento de erros e formata as respostas no padrão RFC 7807
(ProblemDetail).
*/
@RestControllerAdvice
public class GlobalExceptionHandler {
/**
* Intercepta a exceção de recurso não encontrado e retorna HTTP 404 (Not
Found).
*/
@ExceptionHandler(RecursoNaoEncontradoException.class)
public ProblemDetail
tratarRecursoNaoEncontradoException(RecursoNaoEncontradoException ex) {
// Cria a representação padronizada do ProblemDetail para o status 404
ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
HttpStatus.NOT_FOUND,
ex.getMessage()
);
problemDetail.setTitle("Recurso Não Encontrado");
problemDetail.setType(URI.create("https://api.universidade.com/erros/recurso-nao-encontrado"));
problemDetail.setProperty("timestamp", Instant.now());
return problemDetail;
}
/**
* Intercepta falhas de Bean Validation (@Valid) e retorna HTTP 400 (Bad
Request).
* Coleta os campos com erro e suas respectivas mensagens amigáveis.
*/
@ExceptionHandler(MethodArgumentNotValidException.class)
public ProblemDetail tratarMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
HttpStatus.BAD_REQUEST,
"Um ou mais campos contêm erros de validação de contrato."
);
problemDetail.setTitle("Violação de Validação de Dados");
problemDetail.setType(URI.create("https://api.universidade.com/erros/dados-invalidos"));
// Estrutura um mapa com a relação: nomeDoCampo -> mensagemDeErro
Map<String, String> camposComErro = new HashMap<>();
for (FieldError error : ex.getBindingResult().getFieldErrors()) {
camposComErro.put(error.getField(), error.getDefaultMessage());
}
// Estende a carga do ProblemDetail com a lista de erros específicos
problemDetail.setProperty("erros", camposComErro);
problemDetail.setProperty("timestamp", Instant.now());
return problemDetail;
}
/**
* Intercepta quaisquer exceções inesperadas do sistema (HTTP 500).
*/
@ExceptionHandler(Exception.class)
public ProblemDetail tratarExcecoesNaoMapeadas(Exception ex) {
ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
HttpStatus.INTERNAL_SERVER_ERROR,
"Ocorreu um erro interno inesperado no servidor."
);
problemDetail.setTitle("Erro Interno do Servidor");
problemDetail.setType(URI.create("https://api.universidade.com/erros/erro-interno"));
problemDetail.setProperty("timestamp", Instant.now());
return problemDetail;
}
}