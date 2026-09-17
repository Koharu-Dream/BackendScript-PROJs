package com.universidade.catalogo.api.dto;
import com.universidade.catalogo.domain.enums.TipoProduto;
import com.universidade.catalogo.domain.enums.UnidadeMedida;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
/**
* Objeto de Transferência de Dados (DTO) para requisições de criação e
atualização.
* Implementado como Java Record nativo, garantindo imutabilidade e validação
estrutural.
*/
public record ProdutoRequestDTO(
@NotBlank(message = "A descrição do produto é obrigatória.")
@Size(min = 3, max = 100, message = "A descrição deve conter entre 3 e 100 caracteres.")
String descricao,
@NotNull(message = "O tipo do produto é obrigatório.")
TipoProduto tipo,
@NotNull(message = "A unidade de medida é obrigatória.")
UnidadeMedida unidadeMedida,
@NotNull(message = "O valor do produto é obrigatório.")
@Positive(message = "O valor do produto deve ser estritamente maior que zero.")
BigDecimal valor
) {
}