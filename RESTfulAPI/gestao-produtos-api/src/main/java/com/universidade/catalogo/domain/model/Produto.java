package com.universidade.catalogo.domain.model;
import com.universidade.catalogo.domain.enums.TipoProduto;
import com.universidade.catalogo.domain.enums.UnidadeMedida;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Objects;
/**
* Entidade JPA representativa da tabela 'tb_produto' no modelo relacional.
* Modela o estado e os mapeamentos ORM da entidade de domínio.
*/
@Entity
@Table(name = "tb_produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
@Column(name = "descricao", nullable = false, length = 100)
private String descricao;
@Enumerated(EnumType.STRING)
@Column(name = "tipo", nullable = false, length = 20)
private TipoProduto tipo;
@Enumerated(EnumType.STRING)
@Column(name = "unidade_medida", nullable = false, length = 10)
private UnidadeMedida unidadeMedida;
// Definição explícita de precisão total (10 dígitos) e escala (2 casas decimais)
@Column(name = "valor", nullable = false, precision = 10, scale = 2)
private BigDecimal valor;
// Sobrescrita explícita de equals e hashCode baseando-se estritamente na identidade da entidade
@Override
public boolean equals(Object o) {
if (this == o) return true;
if (o == null || getClass() != o.getClass()) return false;
Produto produto = (Produto) o;
return id != null && Objects.equals(id, produto.id);
}
@Override
public int hashCode() {
return getClass().hashCode();
}
}
