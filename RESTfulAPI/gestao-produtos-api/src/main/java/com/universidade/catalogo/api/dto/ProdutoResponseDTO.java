package com.universidade.catalogo.api.dto;
import com.universidade.catalogo.domain.enums.TipoProduto;
import com.universidade.catalogo.domain.enums.UnidadeMedida;
import com.universidade.catalogo.domain.model.Produto;
import java.math.BigDecimal;
/**
* Objeto de Transferência de Dados (DTO) para serialização de respostas da API.
* Encapsula o estado exposto ao cliente externo, isolando o modelo relacional.
*/
public record ProdutoResponseDTO(
Long id,
String descricao,
TipoProduto tipo,
UnidadeMedida unidadeMedida,
BigDecimal valor
) {
/**
* Construtor de conveniência para realizar o mapeamento seguro da entidade de
domínio
* diretamente para a estrutura do DTO de resposta.
*
* @param produto Instância persistida da entidade Produto
*/
public ProdutoResponseDTO(Produto produto) {
this(
produto.getId(),
produto.getDescricao(),
produto.getTipo(),
produto.getUnidadeMedida(),
produto.getValor()
);
}
}