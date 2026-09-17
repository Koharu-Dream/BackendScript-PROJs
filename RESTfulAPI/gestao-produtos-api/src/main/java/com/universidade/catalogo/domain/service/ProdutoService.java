package com.universidade.catalogo.domain.service;
import com.universidade.catalogo.api.dto.ProdutoRequestDTO;
import com.universidade.catalogo.api.dto.ProdutoResponseDTO;
import com.universidade.catalogo.domain.exception.RecursoNaoEncontradoException;
import com.universidade.catalogo.domain.model.Produto;
import com.universidade.catalogo.domain.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
/**
* Camada de serviço de domínio.
* Centraliza as regras de negócio, orquestração de transações e casos de uso de
Produto.
*/
@Service
public class ProdutoService {
private final ProdutoRepository produtoRepository;
// Injeção de Dependência recomendada: Via Construtor explícito (sem uso de @Autowired em campos)
public ProdutoService(ProdutoRepository produtoRepository) {
this.produtoRepository = produtoRepository;
}
/**
* Recupera todos os produtos cadastrados no sistema.
*
* @return Lista contendo os DTOs de resposta correspondentes
*/
@Transactional(readOnly = true)
public List<ProdutoResponseDTO> listarTodos() {
return produtoRepository.findAll()
.stream()
.map(ProdutoResponseDTO::new)
.toList();
}
/**
* Busca um produto pelo seu identificador primário.
*
* @param id Identificador único do produto
* @return DTO contendo os dados do produto encontrado
* @throws RecursoNaoEncontradoException caso o identificador não exista na
base de dados
*/
@Transactional(readOnly = true)
public ProdutoResponseDTO buscarPorId(Long id) {
Produto produto = buscarEntidadePorId(id);
return new ProdutoResponseDTO(produto);
}
/**
* Persiste um novo produto a partir dos dados do DTO de entrada.
*
* @param dto Contrato de entrada contendo dados a persistir
* @return DTO com os dados persistidos e chave primária gerada
*/
@Transactional
public ProdutoResponseDTO salvar(ProdutoRequestDTO dto) {
Produto produto = new Produto();
copiarDtoParaEntidade(dto, produto);
Produto produtoSalvo = produtoRepository.save(produto);
return new ProdutoResponseDTO(produtoSalvo);
}
/**
* Atualiza o estado de um produto pré-existente.
*
* @param id Identificador do produto a ser modificado
* @param dto Novos dados para atualização
* @return DTO refletindo o estado atualizado
*/
@Transactional
public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
Produto produto = buscarEntidadePorId(id);
copiarDtoParaEntidade(dto, produto);
// O Hibernate executará o UPDATE por Dirty Checking na sincronização da transação
Produto produtoAtualizado = produtoRepository.save(produto);
return new ProdutoResponseDTO(produtoAtualizado);
}
/**
* Remove fisicamente um produto a partir de seu identificador.
*
* @param id Identificador do produto alvo de exclusão
*/
@Transactional
public void excluir(Long id) {
if (!produtoRepository.existsById(id)) {
throw new RecursoNaoEncontradoException("Não foi possível excluir. Produto não encontrado com o ID: " + id);
}
produtoRepository.deleteById(id);
}
/**
* Método auxiliar de busca interna da entidade persistente.
*/
private Produto buscarEntidadePorId(Long id) {
return produtoRepository.findById(id)
.orElseThrow(() -> new RecursoNaoEncontradoException("Produto não encontrado com o ID: " + id));
}
/**
* Método de conveniência interno para transferência segura de estado do DTO
para a Entidade.
*/

private void copiarDtoParaEntidade(ProdutoRequestDTO dto, Produto entidade) {
entidade.setDescricao(dto.descricao());
entidade.setTipo(dto.tipo());
entidade.setUnidadeMedida(dto.unidadeMedida());
entidade.setValor(dto.valor());
}
}