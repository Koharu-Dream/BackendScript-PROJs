package com.universidade.catalogo.api.controller;
import com.universidade.catalogo.api.dto.ProdutoRequestDTO;
import com.universidade.catalogo.api.dto.ProdutoResponseDTO;
import com.universidade.catalogo.domain.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;
/**
* Adaptador de entrada HTTP (REST Controller).
* Expõe as operações da API respeitando os padrões de semântica HTTP e REST nível
2.
*/
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
private final ProdutoService produtoService;
public ProdutoController(ProdutoService produtoService) {
this.produtoService = produtoService;
}
/**
* Endpoint para listagem de todos os recursos de produto.
versao01.md 2026-09-05
16 / 26
* Retorna HTTP 200 (OK).
*/
@GetMapping
public ResponseEntity<List<ProdutoResponseDTO>> listarTodos() {
List<ProdutoResponseDTO> lista = produtoService.listarTodos();
return ResponseEntity.ok(lista);
}
/**
* Endpoint para recuperação de um recurso específico pelo ID.
* Retorna HTTP 200 (OK).
*/
@GetMapping("/{id}")
public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) {
ProdutoResponseDTO responseDTO = produtoService.buscarPorId(id);
return ResponseEntity.ok(responseDTO);
}
/**
* Endpoint para criação de um novo recurso.
* Retorna HTTP 201 (Created) e o cabeçalho 'Location' apontando para a URI do
recurso.
*/
@PostMapping
public ResponseEntity<ProdutoResponseDTO> criar(
@RequestBody @Valid ProdutoRequestDTO requestDTO,
UriComponentsBuilder uriComponentsBuilder
) {
ProdutoResponseDTO produtoCriado = produtoService.salvar(requestDTO);
// Constrói dinamicamente a URI canônica: /api/produtos/{id}
URI uri = uriComponentsBuilder.path("/api/produtos/{id}")
.buildAndExpand(produtoCriado.id())
.toUri();
return ResponseEntity.created(uri).body(produtoCriado);
}
/**
* Endpoint para atualização integral de um recurso pelo ID.
* Retorna HTTP 200 (OK).
*/
@PutMapping("/{id}")
public ResponseEntity<ProdutoResponseDTO> atualizar(
@PathVariable Long id,
@RequestBody @Valid ProdutoRequestDTO requestDTO
) {
ProdutoResponseDTO produtoAtualizado = produtoService.atualizar(id,
requestDTO);
return ResponseEntity.ok(produtoAtualizado);
}
/**
* Endpoint para remoção de um recurso pelo ID.
versao01.md 2026-09-05
17 / 26
* Retorna HTTP 204 (No Content) confirmando a exclusão sem corpo de retorno.
*/
@DeleteMapping("/{id}")
public ResponseEntity<Void> excluir(@PathVariable Long id) {
produtoService.excluir(id);
return ResponseEntity.noContent().build();
}
}